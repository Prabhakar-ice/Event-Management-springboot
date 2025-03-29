/**
 * 
 */
document.addEventListener("DOMContentLoaded", function(){
	let input = document.getElementById("registerform");
	if(input){
		
			input.addEventListener("submit", async function(event) {
		    event.preventDefault(); // Prevent form submission
		
		    // Get input values
		    let username = document.getElementById("username").value.trim();
		    let email = document.getElementById("email").value.trim();
		    let password = document.getElementById("password").value.trim();
		
		    // Get error message elements
		    let usernameError = document.getElementById("usernameError");
		    let emailError = document.getElementById("emailError");
		    let passwordError = document.getElementById("passwordError");
		
		    // Reset error messages
		    usernameError.innerText = "";
		    emailError.innerText = "";
		    passwordError.innerText = "";
		
		    let isValid = true;
		
		    // Validate username (should be at least 3 characters)
		    if (username.length < 3) {
		        usernameError.innerText = "Username must be at least 3 characters long.";
		        isValid = false;
		    }
		
		    // Validate email format using regex
		    let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		    if (!emailPattern.test(email)) {
		        emailError.innerText = "Enter a valid email address.";
		        isValid = false;
		    }
		
		    // Validate password (should be at least 6 characters)
		    if (password.length < 6) {
		        passwordError.innerText = "Password must be at least 6 characters long.";
		        isValid = false;
		    }
			
			try{
			//hitting the register url
			let rest = await fetch("http://localhost:8081/api/register?username="+username+"&email="+email+"&password="+password,{
				method: 'POST'
				
			})
			
			if(!rest.ok){
				throw new Error(`HTTP Error! Status: ${rest.status}`);
			}
			}
			catch(error){ console.error("Error:", error);}
			
		    // If all fields are valid, redirect to the next page
		    if (isValid) {
				window.alert("you have register successfully No you can login")
		       window.location.href = "login.html"; // Redirect to welcome page
		    }
	   });
	}
	else {
	        console.error("Element with ID 'registerform' not found!");
	    }
});


