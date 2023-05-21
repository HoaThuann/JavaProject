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
		//click image to choose default image
		var defaultFileIndex;
		function getDefaultImage(event){
			var clickedImage = event.target;
			// Loại bỏ CSS border cho tất cả các phần tử hình ảnh khác
		   
		    const images = selectedImagesContainer.querySelectorAll('.selected-image img');
		    images.forEach((image) => {
		        image.style.border = '';
		    });
		    
		    const image = clickedImage.querySelector('img');
			image.style.border = '2px solid red';
			defaultFileIndex =image.getAttribute('data-index');
			
			
		}
		// Lấy khung hiển thị ảnh đã chọn
		const selectedImagesContainer = document.getElementById('selected-images');

		// Lấy khung chọn file
		const fileInput = document.getElementById('file-input');

		// Mảng chứa các file đã chọn
		let selectedFiles = [];
		
		// Xử lý sự kiện khi người dùng chọn file
		fileInput.addEventListener('change', imageChange);
		//selectedImagesContainer.addEventListener('change', imageChange);
		function imageChange(){
			//set defaultFileIndex
			defaultFileIndex=0;
			// Xóa các ảnh đã hiển thị trước đó
			  selectedImagesContainer.innerHTML = '';

		    // Lấy danh sách các file đã chọn
		    if(fileInput!=null){
				const files = fileInput.files;
				
				// Thêm các file mới vào mảng
			    for (let i = 0; i < files.length; i++) {
			        selectedFiles.push(files[i]);
			    }
			}

		    // Hiển thị các ảnh đã chọn trên màn hình
		    for (let i = 0; i < selectedFiles.length; i++) {
		        // Tạo khung chứa ảnh
		        const imageContainer = document.createElement('div');
		        imageContainer.className = 'selected-image';
		       

		        // Tạo đối tượng ảnh
		        const image = document.createElement('img');
		        image.src = URL.createObjectURL(selectedFiles[i]);
		       
		        //save index image to get default image
		        image.setAttribute('data-index', i); 
				imageContainer.addEventListener('click', getDefaultImage);
		        

		        // Tạo nút xóa ảnh
		        const removeButton = document.createElement('div');
		        removeButton.className = 'remove-selected-image';
		       	removeButton.innerHTML = '<i class="bi bi-x-square"></i>';
		        removeButton.style.marginBottom = '-4px';
				
				// Thêm nút xóa vào khung chứa
		        imageContainer.appendChild(removeButton);
		     // Thêm ảnh vào khung chứa
		        imageContainer.appendChild(image);
				
		        // Thêm khung chứa ảnh vào khung hiển thị ảnh đã chọn
		        selectedImagesContainer.appendChild(imageContainer);
		        // Xử lý sự kiện khi người dùng click nút xóa ảnh
		        removeButton.addEventListener('click', function(event){
				
		            selectedFiles.splice(i, 1);
		           	fileInput.value = null;
		            imageChange();
		        });
		      
		    }	        
		};
	
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
		
		//xử lý thêm dữ liệu nếu edit
		var url ;
		var type;
		$('#saveProduct').on('show.bs.modal', function (e) {
                $("#id").val("");
                $("#title").val("");
                $("#price").val("");
                $("#currentPrice").val("");
                $("#description").val("");
               $('input[name="inlineRadioOptions"]').prop('checked', false);
                url="/product/add";
                type="POST";
                var button = e.relatedTarget;
                var productId = $(button).data('id');
               	var title =  $(button).parents('tr').find('.title').text();
                var gender = $(button).parents('tr').find('.gender').text();
                var price =  $(button).parents('tr').find('.price').text();
                var currentPrice =  $(button).parents('tr').find('.currentPrice').text();
                var description =  $(button).parents('tr').find('.description').text();
               
                if( typeof productId !== 'undefined'){
                    
                   url="/product/update";
                   type="PUT";
                  $("#title").html("Update product");
                   $('#save-product-value').text('Update');
                    document.getElementById('id').value =productId;
                    document.getElementById('title').value = title;
					document.getElementById('price').value = price;
					document.getElementById('currentPrice').value = currentPrice;
					if(gender=='Women'){
						document.querySelector('input[name="inlineRadioOptions"][value="false"]').checked = true;
					}else{
						document.querySelector('input[name="inlineRadioOptions"][value="true"]').checked = true;
					}
					
					document.getElementById('description').value = description;
                    
                    
                  
               }
            
           
        });
		//xử lí dữ liệu details product
		$('#detailsProduct').on('show.bs.modal', function (e) {
			var button = e.relatedTarget;
			
               $("#title-details").html($(button).parents('tr').find('.title').text());
				$("#create-at-details").html($(button).parents('tr').find('.createAt').text());
				$("#update-at-details").html($(button).parents('tr').find('.updateAt').text());
				$("#category-details").html($(button).parents('tr').find('.category').text());
				$("#size-details").html($(button).parents('tr').find('.size').text());
				$("#color-details").html($(button).parents('tr').find('.color').text());
				$("#gender-details").html($(button).parents('tr').find('.gender').text());
				$("#price-details").html($(button).parents('tr').find('.price').text());
                $("#current-price-details").html( $(button).parents('tr').find('.currentPrice').text());
               $("#description-details").html( $(button).parents('tr').find('.description').text());
              
           
        });
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
		    
		    if(url=="/product/update"){
				 formData.append('id', parseInt(document.getElementById('id').value));
				
			}
		    //default image index
		    if(defaultFileIndex!=null){
				formData.append('defaultFileIndex', parseInt(defaultFileIndex));
			}else{
				formData.append('defaultFileIndex', 0);
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
		        url: url,
		        type: type,
		        contentType: false,
		        processData: false,
		        data: formData,
		        dataType: 'json',
		        cache: false,
		        success: function(data) {
		        	alert(data.message);
		        	clearFrom();
		        	location.reload();
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
	
	 //DELETE Product
		//set Product id for modal
		$('#deleteProduct').on('show.bs.modal', function (e) {
	                var button = e.relatedTarget;
	               var productId = $(button).data('id');
	                $('#productIdDeleteModal').val(productId);	               
	                
	   })
	   //delete addtribute value when click button with id 'delete-attribute-value' in modal
	   $(document).on("click","#delete-product-value",function(e) {
		   var productId = document.getElementById("productIdDeleteModal").value;
		
		    $.ajax({
		      url: '/product/delete?productId='+productId,
		      type: 'DELETE',
		      contentType: 'json',
		      success: function(data) {
		        // close modal
		        $('#deleteProduct').modal('hide');
		        //notice that the deletion was successful
		        alert(data);
		        //reload pagination
		        loadData(currentPageForEditAndDelete);
		      },
		      error: function(jqXHR, textStatus, errorThrown){
		    	  if (jqXHR.status === 400) {
		    	        var error = jqXHR.responseJSON || jqXHR.responseText;
		    	        $('#deleteProduct').modal('hide');
		    	        alert(error)
		    	        loadData(currentPageForEditAndDelete);
		    	    } else {
		    	        // xử lý lỗi khác
		    	        console.log(textStatus);
		    	        console.log(errorThrown);
		    	    }
		      }})
	        });
	        
	var size;
	//get current page to load page when delete or update a record
	var currentPageForEditAndDelete;
	$('table tbody').on('click', 'tr', function() {
		var firstRow = $(this).closest('tbody').find('tr:first');
		var startIndex = firstRow.children('td:first').text();
		currentPageForEditAndDelete = Math.ceil((startIndex - 1) / size);
	});
	//get product page
	 loadData(0);
	 var imageNames = [] ;
	const uploadDirectory = "src/main/resources/static/upload/";
	function loadData(page) {
			var table = "#product-table tbody";
		    $.ajax({
		      url: '/product/getProductPage',
		      type: 'GET',
		      dataType: 'json',
		      data: {page : page},
		      success: function(data) {
		        //clear table
		        $(table).empty();
		
		        //add record
		        $.each(data.productResponseDtos, function(index, product) {
		        	var row = $('<tr>');
		            row.append($('<td>').text(data.currentPage * data.size + index+1));
		            $.each(product.images, function(i,image) {
						imageNames.push(product.images[i].inmageForSave);
  						if (product.images[i].isDefault == true) {
							  
							  row.append(
							  $('<td>').attr({
							    'data-imageList': imageNames
							  }).append(
							    $('<img>').attr('src', "../upload/"+image.inmageForSave+"?v=1")
							  ).addClass('default-img')
							);
				  		}
				  	});
		           row.append($('<td>').text(product.title).addClass('title'));
		           row.append($('<td>').text(product.categoryName).addClass('category'));
		           if(product.productAttributeValue!=null && product.productAttributeValue.size!=null && product.productAttributeValue.color!=null){
					   
					    row.append($('<td>').text(product.productAttributeValue.size).addClass('size').attr("data-size-id",product.productAttributeValue.sizeId));
		           		row.append($('<td>').text(product.productAttributeValue.color).addClass('color').attr("data-size-id",product.productAttributeValue.colorId));
				   }else if(product.productAttributeValue!=null && product.productAttributeValue.size!=null){
					  
					   row.append($('<td>').text(product.productAttributeValue.size).addClass('size').attr("data-size-id",product.productAttributeValue.sizeId));
		           		row.append($('<td>').addClass('color'));
				   }else if(product.productAttributeValue!=null && product.productAttributeValue.color!=null){
					   
					   row.append($('<td>').addClass('size'));
		           		row.append($('<td>').text(product.productAttributeValue.color).addClass('color').attr("data-size-id",product.productAttributeValue.colorId));
				   }else{
					   row.append($('<td>').addClass('size'));
					   row.append($('<td>').addClass('color'));
				   }
		          
		            if(product.gender){
						row.append($('<td>').text("Man").addClass('gender'));
					}else{
						row.append($('<td>').text("Women").addClass('gender'));
					}
		           // var formattedPrice = product.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
		            row.append($('<td>').text(product.price).addClass('price'));
		            row.append($('<td>').text(product.currentPrice).addClass('currentPrice'));
		            row.append($('<td>').text(product.description).hide().addClass('description'));
		            row.append($('<td>').text(product.createAt).hide().addClass('createAt'));
		            row.append($('<td>').text(product.updateAt).hide().addClass('updateAt'));
		            
		            
		            row.append($('<td>').addClass('details-button').html("<button data-bs-toggle='modal' data-bs-target='#detailsProduct'   class='btn' type='button'>Details</button>"));
		            
		            row.append($('<td>').attr({
		            	'data-id': product.id,
		                'data-bs-toggle': 'modal',
		                'data-bs-target': '#saveProduct'
		            }).addClass('text-primary').html('<i class="bi bi-pencil-square"></i>Edit'));
		            
		            row.append($('<td>').attr({
		            	'data-id': product.id,
		                'data-bs-toggle': 'modal',
		                'data-bs-target': '#deleteProduct'
		            }).addClass('text-danger').html('<i class="bi bi-trash"></i>Delete'));
		            $(table).append(row);
		        });
		         pagination = '';
		         currentPage = data.currentPage;
		         totalPages = data.totalPages;
		         size = data.size;
		        //create pagination 
		       createPagination("#paging",pagination,currentPage,totalPages );
		      },
		      error: function(){}})}
	      
	      
	      //function to create pagination after call ajax
	      function createPagination(navId,pagination,currentPage,totalPages ){
	    	  $(navId).empty();
	          
	          pagination += '<ul class="pagination">';
	          pagination += '<li ' + (currentPage > 0 ? '' : 'class="page-item disabled"') + '><a class="page-link" onclick="' + (currentPage > 0 ? 'loadData(' + (currentPage - 1) +')' : 'return false;') + '" aria-label="Previous"><span aria-hidden="true">&laquo;</span><span class="sr-only"></span></a></li>';

	         
	              var startPage = currentPage > 2 ? currentPage - 2 : 0;
	              var endPage = currentPage + 2 < totalPages - 1 ? currentPage + 2 : totalPages - 1;

	              for (var i = startPage; i <= endPage; i++) {
	                  pagination += '<li ' + (currentPage === i ? 'class="page-item active"' : '') + '><a class="page-link" onclick="loadData(' + i +')">' + (i + 1) + '</a></li>';
	              }
	             //${page.totalPages > 5 && page.number < page.totalPages - 3}
	              if ((totalPages > 5) &&  (currentPage < totalPages-3)) {
	                  pagination += '<li class="page-item disabled"><a class="page-link" >...</a></li>';
	              }
	             
	              if ((totalPages > 1) && (totalPages-1 != currentPage) && (totalPages-2 != currentPage) && (currentPage<totalPages-3)) {
	                  pagination += '<li><a class="page-link" onclick="loadData(' + (totalPages - 1) +')" >' + totalPages + '</a></li>';
	              }

	          pagination += '<li ' + (currentPage < totalPages - 1 ? '' : 'class="page-item disabled"') + '><a class="page-link" onclick="' + (currentPage < totalPages - 1 ? 'loadData(' + (currentPage + 1) +')' : 'return false;') + '" aria-label="Next"><span aria-hidden="true">&raquo;</span><span class="sr-only"></span></a></li>';
	          pagination += '</ul>';
	           
	           $(navId).append(pagination);
	      }