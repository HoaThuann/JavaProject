//click on the input box, the error line will disappear
		var inputList = ['title','category','price','currentPrice','description','file-input'];
		
		inputList.forEach(function(inputName) {
			
		  var input = document.getElementById(inputName);
		  var error = document.getElementById(inputName + '-error');
		  input.addEventListener("focus", function() {
			 error.textContent = '';
		  });
		});
		
		var radios = document.getElementsByName("inlineRadioOptions");
		var radioError = document.getElementById("gender-error");
		radios.forEach(function(radio) {
		  radio.addEventListener("focus", function() {
			 radioError.textContent = '';
		  });
		});
//loadCategory();
		function loadCategory() {
		    const categorySelect = document.getElementById('category');
		    $.ajax({
		        url: "/category/getAll",
		        type: "GET",
		        dataType: 'json',
		        success: function(categories) {
		        	if (categories.length != 0) {
		        		categorySelect.innerHTML = "<option value='' selected>Choose category</option>";
			    		  categories.forEach(function(category) {
			    			    const optionElement = document.createElement('option');
			    			    optionElement.value = category.id;
			    			    optionElement.textContent = category.name;
			    			    categorySelect.appendChild(optionElement);
			    			});
			    		  
			          } else {
			              
			          }
		        },
		        error: function(jqXHR, textStatus, errorMessage) {
		            var error = jqXHR.responseJSON || jqXHR.responseText;
		        }
		    });
		}
		//load color 
		
		function loadAttributeValue(attributeName,selectId){
			const colorSelect = document.getElementById(selectId);
			$.ajax({
			    url: "/attributeValue/getAll",
			    type: "GET",
			    dataType: 'json',
			    data:{attributeName: attributeName},
			    success: function(colors) {
			    	  if (colors.length != 0) {
			    		  colorSelect.innerHTML = "<option value='' selected>Choose "+attributeName +"</option>";
			    		  colors.forEach(function(color) {
			    			    const optionElement = document.createElement('option');
			    			    optionElement.value = color.id;
			    			    optionElement.textContent = color.name;
			    			    colorSelect.appendChild(optionElement);
			    			});
			    		  
			          } else {
			             
			          }
			    },
			    error: function(jqXHR, textStatus, errorMessage) {
			    	
			    }
			});
		}
		
		// Lấy khung hiển thị ảnh đã chọn
		const selectedImagesContainer = document.getElementById('selected-images');

		// Lấy khung chọn file
		const fileInput = document.getElementById('file-input');

		// Mảng chứa các file đã chọn
		let selectedFiles = [];

		// Xử lý sự kiện khi người dùng chọn file
		fileInput.addEventListener('change', () => {
			// Xóa các ảnh đã hiển thị trước đó
			  selectedImagesContainer.innerHTML = '';

		    // Lấy danh sách các file đã chọn
		    const files = fileInput.files;
		    
		    

		    // Thêm các file mới vào mảng
		    for (let i = 0; i < files.length; i++) {
		        selectedFiles.push(files[i]);
		    }

		    // Hiển thị các ảnh đã chọn trên màn hình
		    for (let i = 0; i < selectedFiles.length; i++) {
		        // Tạo khung chứa ảnh
		        const imageContainer = document.createElement('div');
		        imageContainer.className = 'selected-image';
		        imageContainer.style.marginRight='10px';

		        // Tạo đối tượng ảnh
		        const image = document.createElement('img');
		        image.src = URL.createObjectURL(selectedFiles[i]);
		        image.style.width = '100px';
		        image.style.height = '100px';
		        image.style.objectFit = 'cover';
		        image.style.objectPosition = 'center center';

		        

		        // Tạo nút xóa ảnh
		        const removeButton = document.createElement('div');
		        removeButton.className = 'remove-selected-image';
		       	removeButton.innerHTML = '<i class="bi bi-x-square"></i>';
		        removeButton.style.marginBottom = '-4px';

		        // Xử lý sự kiện khi người dùng click nút xóa ảnh
		        removeButton.addEventListener('click', () => {
		            // Xóa khung chứa ảnh
		            selectedImagesContainer.removeChild(imageContainer);

		            // Xóa ảnh khỏi mảng
		            const index = selectedFiles.indexOf(files[i]);
		            if (index > -1) {
		                selectedFiles.splice(index, 1);
		            }
		            //kiểm tra mảng chứa ảnh được chọn rỗng thì input nulls
		            //if (selectedFiles.length === 0) {
		              //  document.getElementById('file-input').value = null;
		              //}
		        });

		        // Thêm nút xóa vào khung chứa
		        imageContainer.appendChild(removeButton);
		     // Thêm ảnh vào khung chứa
		        imageContainer.appendChild(image);
				
		        // Thêm khung chứa ảnh vào khung hiển thị ảnh đã chọn
		        selectedImagesContainer.appendChild(imageContainer);
		        
		      //set lại số lượng file hiển thị trong input file
			 // document.getElementById('file-input').textContent = files.length+" files";
		    }
		});
		
		//buttoon clear image
		function clearImage() {
			document.getElementById('file-input').value = null;
			//xóa khung chưa ảnh
			selectedImagesContainer.innerHTML = '';
			//xóa trong mảng chứa file
			selectedFiles.splice(0, selectedFiles.length);
			//xóa lỗi
			$("#file-input-error").text('');
		}
		
		//save product
		function saveProduct() {
			
			var title = document.getElementById('title').value.trim();
		    var category = document.getElementById("category").value;
		    var sizeAttributeValue = document.getElementById('sizeSelect').value;
		    var colorAttributeValue = document.getElementById('colorSelect').value;
		    var price = document.getElementById('price').value;
		    var currentPrice = document.getElementById('currentPrice').value;
		    var gender = document.querySelector('input[name="inlineRadioOptions"]:checked');
		    var description = document.getElementById('description').value.trim();
		    var formData = new FormData();
			
		    for (let i = 0; i < selectedFiles.length; i++) {
		        formData.append('file', selectedFiles[i]);
		    }
		    //set title
		    formData.append('title', title);
		    //set category
		    
		    if(category!=null && category!=""){
		    	formData.append('category', parseInt(document.getElementById("category").value));
		    }else{
		    	formData.append('category', '');
		    }
		    //setSizeAttributeValue
		    if(sizeAttributeValue!=null && sizeAttributeValue!=""){
		    	formData.append('sizeAttributeValue', parseInt(document.getElementById('sizeSelect').value));
		    }else{
		    	formData.append('sizeAttributeValue', 0);
		    }
		    
		  	//setColorAttributeValue
		    if(colorAttributeValue!=null && colorAttributeValue!=""){
		    	formData.append('colorAttributeValue', parseInt(document.getElementById('colorSelect').value));
		    }else{
		    	formData.append('colorAttributeValue', 0);
		    }
		    
		    //setGender
		    if(gender!=null && gender!=""){
		    	formData.append('gender', gender.value);
		    }
		    
		    if(price!=null && price!=""){
		    	formData.append('price', parseFloat(document.getElementById('price').value));
		    }else{
		    	formData.append('price', -1);
		    }
		    if(currentPrice!=null && currentPrice!=""){
		    	formData.append('currentPrice', parseFloat(document.getElementById('currentPrice').value));
		    }else{
		    	formData.append('currentPrice', 0);
		    }
		    
		    
		    formData.append('description', description);
		
		    $.ajax({
		        url: "/product/add",
		        type: "POST",
		        contentType: false,
		        processData: false,
		        data: formData,
		        dataType: 'json',
		        cache: false,
		        success: function(data) {
					
		        	alert(data.message);
		        },
		        error: function(jqXHR, textStatus, errorMessage) {
					 
		             	  if (jqXHR.status === 400) {
			      	       var errors = jqXHR.responseJSON;
			      	        if (errors.hasOwnProperty("bindingErrors")) {
			      	            var bindingErrors = errors["bindingErrors"];
			      	            for (var i = 0; i < bindingErrors.length; i++) {
			      	                var error = bindingErrors[i];
								    // Hiển thị thông báo lỗi trong tag ID đã tạo
								    $("#" + error.field + "-error").text(error.defaultMessage);
			      	            }
			      	        }
			      	        if (errors.hasOwnProperty("fileErrors")) {
							    var fileError = errors["fileErrors"];
							    $("#file-input-error").text(fileError);
							}
							if (errors.hasOwnProperty("titleDuplicate")) {
							    var titleError = errors["titleDuplicate"];
							    $("#title-error").text(titleError);
							}
			      	    }else{
							   alert("Sorry! The system have errors!") 
						  }
		        	}
		    	});
	}
	//clear form add product
	function clearFrom(){
		
		var inputId = ['title','price','currentPrice','currentPrice','description'];
		inputId.forEach(function(input){
			
			document.getElementById(input).value ='';
		})
		
		var selectId =['category','sizeSelect','colorSelect'];
		selectId.forEach(function(select) {
		 document.getElementById(select).selectedIndex = 0;
		});
		
		var radios = document.getElementsByName("inlineRadioOptions");
		radios.forEach(function(radio) {
		  radio.checked = false;
		});
		
		var errors = ['title-error','price-error','currentPrice-error','currentPrice-error','description-error','category-error','sizeSelect-error','colorSelect-error','gender-error','file-input'];
		errors.forEach(function(error){
			document.getElementById(error).textContent='';
		})
		
		clearImage();
	}