/**
 * 
 */

const range = 16;
const set = 17;

const string = "VAR";
const number = ["DEC", "INT"];
const bool = "BOO";

document.getElementById("categorySelector").addEventListener("change", fetchAttributes);

function fetchAttributes(event) {
	let value = event.target.value;
	if (!value) {
		document.getElementById("attributeFieldset").innerHTML = "";
		return;
	}
	
	let request = new XMLHttpRequest();
	let url = new URL(location.origin +"/YourPCWeb/AttributeServlet");
	url.searchParams.set("categoryid", document.getElementById("categorySelector").value);
	url.searchParams.set("unassigned", false);
	
	request.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	       document.getElementById("attributes").innerHTML = jsonToForm(request.responseText);
	    }
	};
	
	
	request.open("get", url);
	request.send();
}

function jsonToForm(json) {
	
	const obj = JSON.parse(json);
	let elements = [];
	
	for (let i = 0; i < obj.length; i++) {
		elements.push("<label for='"+obj[i].id +"'>"+obj[i].name +"</label>")
	}
	
	return elements.join("<br />");
}