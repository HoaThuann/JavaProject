const loginForm = document.getElementById('login-form');
	const emailInput = document.getElementById('email-input');
	const passwordInput = document.getElementById('password-input');
	const emailError =document.getElementById('email-error');
	const passwordError =document.getElementById('password-error');
	
	//if user forcus input is set value error empty
	
	
	loginForm.addEventListener('submit', async (event) => {
	  event.preventDefault();
	  emailInput.addEventListener("focus", function() {
		  emailError.textContent = ""; 
		});
	passwordInput.addEventListener("focus", function() {
		passwordError.textContent = ""; 
		});
	  const formData = new FormData(loginForm);
	  const email = formData.get('email');
	  const password = formData.get('password');
	  
	  //check username or email is empty
	  function isEmpty(str) {
		  return (!str || str.length === 0);
	  }
	  var isValid = true;
	  function validateForm(){
		 if (isEmpty(email)) {
			  // Hiển thị thông báo lỗi cho người dùng
			  emailError.textContent = 'Please enter your email!';
			  isValid = false;
		}

		if (isEmpty(password)) {
			  // Hiển thị thông báo lỗi cho người dùng
			  passwordError.textContent = 'Please enter your password!';
			  isValid = false;
		}
		return isValid;
	  }
	  
	  //if username or email is not empty call /user/checkLogin
		if(validateForm()){
			const response = await fetch('/user/checkLogin', {
			    method: 'POST',
			    headers: {
			      'Content-Type': 'application/json'
			    },
			    body: JSON.stringify({
			      email: email,
			      password: password
			    })
			  });
			//if the authentication is successful, add token in header 
			  if (response.ok) {
				  
				 const data = await response.json();
			     const token = data.token;
			     const role = data.role;
			     
			      const homeResponse = await fetch('/user/home', {
			        headers: {
			          'Authorization': 'Bearer ' + token
			        }
			      });
			      
			      const homeAdminResponse = await fetch('/user/homeAdmin', {
				        headers: {
				          'Authorization': 'Bearer ' + token
				        }
				      });
			
			      if (homeResponse.ok) {
			        if (role === 'USER') {
			          window.location.href = "/user/home";
			        } else if (role === 'ADMIN') {
			          window.location.href = '/user/homeAdmin';
			        }
			      } else {
			        alert('Log in failed!');
			      }
			  
			  } else {
			    alert("Email or password not match! Please try again!");
			  }
		}
	});