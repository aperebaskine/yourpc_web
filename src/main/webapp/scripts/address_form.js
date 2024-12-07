/**
 * 
 */

window.onload = function () {
	
}

function requestData(event, servletPath) {
	let value = event.target.value;

	if (value) {
		let request = new XMLHttpRequest();
		let url = new URL(location.origin + contextPath + servletPath);
		url.searchParams.set("categoryid", document.getElementById("categorySelector").value);
		url.searchParams.set("unassigned", false);

		request.onreadystatechange = function () {
			if (this.readyState == 4 && this.status == 200) {
				populateFieldset(request.responseText);
			}
		};

		request.open("get", url);
		request.send();
	}
}