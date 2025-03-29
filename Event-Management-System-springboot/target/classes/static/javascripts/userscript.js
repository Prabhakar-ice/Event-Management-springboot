/**
 * 
 */


// Search Box
document.addEventListener("DOMContentLoaded",function(){
	
	const username = sessionStorage.getItem("username");
	
	document.getElementById("usernamedisplay").innerText = username.toUpperCase();
	console.log("Username:", username);
	
	let input = document.getElementById('searchBox');
	console.log("out");
	  input.addEventListener('keyup', function () {
		console.log("in");
	    const filter = input.value.toLowerCase();
	    const rows = document.querySelectorAll('#userevents tr');

	    rows.forEach(row => {
	      const text = row.textContent.toLowerCase();
	      row.style.display = text.includes(filter) ? '' : 'none';
	    });    

	  });
});



function logout(){
	
	
	localStorage.removeItem("token");
	console.log("removed token")
	
	sessionStorage.clear();
	console.log("session cleared");
	
	alert("You have been logged out.");
	
	window.location.href = "index.html";
	
}
