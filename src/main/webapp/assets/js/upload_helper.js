var droppedFiles = {}; // For drag 'n drop functionality
function previewImage(input, dropZone) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();
        reader.onload = function(e) {
			let img = new Image();
            img.src = e.target.result;
            
            // Set the CSS properties of the image to fill the drop-zone
            img.style.width = "100%";
            img.style.height = "100%";
            img.style.objectFit = "cover";
            
            // Add the drop animation
            img.style.animation = "dropAnimation 0.4s cubic-bezier(.22,.68,0,1.71)";
            dropZone.css("overflow", "visible");

            // Remove any existing images in the drop-zone before appending the new one
            dropZone.find("img").remove();
            dropZone.prepend(img);

            // Hide the drop message
            dropZone.find(".drop-message").hide();

            // Store the original image in localStorage
            localStorage.setItem(input.name, e.target.result);
            
            // Listen for the end of the animation and then set the overflow back to 'hidden'
            img.addEventListener('animationend', function() {
                dropZone.css("overflow", "hidden");
                animateShine(dropZone, 300);
            });
            
            // Send image and square_id to server for uploading
            $.ajax({
                url: '/AnimeIMGDatabase/api/upload',
                type: 'POST',
                data: {
			        image: img.src,
			        square_id: dropZone.data('square_id')
			    },
                success: function(response) {
                    console.log('Upload successful!');
                    console.log(response);
                },
                error: function(response) {
                    console.log('Upload error: ', response);
                }
            });
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        // Show the drop message when no image is selected
        dropZone.find(".drop-message").show();
    }
}

function handleDragging(e) {
    e.preventDefault();
    e.stopPropagation();
}

function handleDragEnterLeave(e) {
    e.preventDefault();
    e.stopPropagation();
    let dropZone = $(e.target).closest(".square");
    dropZone.toggleClass("dragging", e.type === "dragenter");
}

function handleDrop(e, onlyImagesAllowed, container) {
    e.preventDefault();
    e.stopPropagation();
    let dropZone = $(e.target).closest(".square");
    let files = e.originalEvent.dataTransfer.files;
    let input = dropZone.parent().siblings("input[type='file']")[0];

    // Store the dropped files in the droppedFiles variable
    droppedFiles[input.id] = files;

    dropZone.removeClass("dragging");

    // Call the previewImage function to show the image preview
    if (files.length) {
        // Modify the input's files property to store the dropped files
        input.files = files;

        // Call previewImage or previewFile depending on onlyImagesAllowed
        let type;
        if (onlyImagesAllowed) {
            previewImage(input, dropZone);
            type = 'image';
        } else {
            previewFile(input, dropZone, container);
            type = 'document';
        }

        // After displaying the files, call the uploadFiles function
        uploadFiles(files, type);
    } else {
        dropZone.html(``);
    }
}

function setupDropZone(dropZoneSelector, onlyImagesAllowed = false, container = '') {
    let dropZones = $(dropZoneSelector);

    dropZones.on("dragenter", handleDragEnterLeave);
    dropZones.on("dragleave", handleDragEnterLeave);
    dropZones.on("dragover", handleDragging);
    dropZones.on("drop", function(e) {
        handleDrop(e, onlyImagesAllowed, container);
    });

    $("input[type='file']").on("change", function () {
        let dropZone = $(this).siblings().find(dropZoneSelector);
        if (this.files.length) {
            if (onlyImagesAllowed) {
                previewImage(this, dropZone);
            } else {
                previewFile(this, dropZone, container);
            }
        } else {
            dropZone.html(``);
        }
    });
}

function resetDropZone(dropZoneSelector) {
    let dropZones = $(dropZoneSelector);
    
    dropZones.each(function() {
        let dropZone = $(this);

        // Remove any existing images in the drop-zone
        dropZone.find("img").remove();

        // Show the drop message
        dropZone.html(``);
    });

    // Clear input files
    $("input[type='file']").val('');
}

function displayFile(file, container) {
    // Calculate file size in KB or MB
    let fileSize = file.size < 1024 * 1024 ? 
        (file.size / 1024).toFixed(2) + ' KB' : 
        (file.size / 1024 / 1024).toFixed(2) + ' MB';

    let fileName = file.name;
    let fileType = fileName.split('.').pop().toLowerCase();
    let fileDate = new Date().toLocaleDateString("en-US", { month: 'long', day: 'numeric', year: 'numeric' });
    let iconClass = getFileIconClass(fileType);

    let fileHTML = `
        <div class="mt-2 tewi-card">
            <div class="tewi-card-body">
                <div class="text-primary font-clemente">
                    <i class="${iconClass}"></i> ${fileName}
                </div>
                <div class="mt-2">
                    <span style="font-size: 14px; opacity: 0.75;">${fileSize} • ${fileDate}</span>
                </div>
                <div class="progress mt-2">
                    <div class="progress-bar" data-file_name="${file.name}" role="progressbar" style="width: 0%;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">0%</div>
                </div>
            </div>
        </div>`;

    $(container).prepend(fileHTML);
}
function previewFile(input, dropZone, container) {
    // Get the list of files from the input element
    let files = input.files;
    for (let i = 0; i < files.length; i++) {
        let file = files[i];

        // Get file extension
        let fileExtension = file.name.split('.').pop().toLowerCase();

        // Array of vulnerable file types
        let vulnerableFileExtensions = ['exe', 'scr', 'dll', 'vbs', 'js', 'bat', 'com', 'cmd', 'jse', 'pif', 'vb', 'vbe'];

        // Check if file is a vulnerable type
        if (vulnerableFileExtensions.includes(fileExtension)) {
            // Handle vulnerable file type (show error message or something)
            console.log("Cannot upload potentially unsafe file type.");
            continue;
        }

        // If the file is an image, show a preview of it
        if (file.type.startsWith("image/")) {
            previewImage({ files: [file] }, dropZone);
        } else {
            // Handle non-image file type (e.g., show a generic file icon and filename)
            displayFile(file, container);
        }
    }
}
function updateProgressBar(progress, filename) {
    let progressBar = $(`.progress-bar[data-file_name="${filename}"]`);
    let progressPercentage = Math.round((progress.loaded * 100) / progress.total);
    progressBar.css('width', progressPercentage + '%').attr('aria-valuenow', progressPercentage);
    if (progressPercentage === 100) {
        animateShine(progressBar);
        progressBar.text(`Upload Complete`);
    } else {
        progressBar.text(`Uploading... ${progressPercentage}%`);
    }
}

function uploadFiles(files, type) {
    if (type !== 'document') {
        return;
    }
}