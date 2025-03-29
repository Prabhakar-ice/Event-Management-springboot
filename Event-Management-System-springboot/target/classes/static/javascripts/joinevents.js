
document.addEventListener("DOMContentLoaded",function(){
	
	let input = document.getElementById('searchBox');
	console.log("out");
	  input.addEventListener('keyup', function () {
		console.log("in");
	    const filter = input.value.toLowerCase();
	    const rows = document.querySelectorAll('#allevents tr');

	    rows.forEach(row => {
	      const text = row.textContent.toLowerCase();
	      row.style.display = text.includes(filter) ? '' : 'none';
	    });    

	  });
});


function showallevents(){
	
	fetch("http://localhost:8081/api/events/")
	.then(response => response.json())
	.then(allevents => {
		
		const table = document.getElementById("allevents");
		
		allevents.forEach(events => {
			var row = `<tr>
			<td>${events.title}</td>
			<td>${events.description}</td>
			<td>${events.time}</td>
			<td>${events.location}</td>
			<td>${events.date}</td>
			<td>
				<button class="btn btn-warning" onclick="handleevent(${events.id})">
				Join
				</button>
			</td>
			</tr>`;
			console.log(events.title);
			table.innerHTML += row;
		});
	})
	.catch(error => console.error("Error fetching events:", error));
}

function logout(){
	
	sessionStorage.clear();
	console.log("session cleared")
	
	localStorage.removeItem("token");
	console.log("local cleared")
	
	window.location.href = "index.html";
}

function handleevent(eventid){
	console.log(eventid);
}




document.addEventListener("DOMContentLoaded", function (){
	let input = document.getElementById('searchBox');
	  input.addEventListener('keyup', function () {
	    
	    const filter = input.value.toLowerCase();
	    const rows = document.querySelectorAll('#allevents tr');

	    rows.forEach(row => {
	      const text = row.textContent.toLowerCase();
	      row.style.display = text.includes(filter) ? '' : 'none';
	    });    


	  });
});
