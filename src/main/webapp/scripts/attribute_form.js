/**
 * 
 */

// Attribute value handling modes
const range = 0x10;
const set = 0x11;

// Attribute data types
const string = "VAR";
const integer = "INT";
const double = "DEC";
const boolean = "BOO";

// Attribute fieldset element
const fieldset = document.getElementById("attributes");

window.onload = function () {
	let categorySelector = document.getElementById("categorySelector");
	categorySelector.addEventListener("change", resetFieldset);
	categorySelector.addEventListener("change", requestData);
}

function resetFieldset() {
	fieldset.innerHTML = "";
}

function requestData(event) {
	let value = event.target.value;

	if (value) {
		let request = new XMLHttpRequest();
		let url = new URL(location.origin + "/YourPCWeb/AttributeServlet");
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

function populateFieldset(json) {
	const attributes = JSON.parse(json);

	for (let i = 0; i < attributes.length; i++) {
		createAttributeInput(attributes[i]);
	}
}

function createAttributeInput(attribute) {

	fieldset.appendChild(createLabel(toInputName(attribute), attribute.name));

	if (attribute.dataTypeIdentifier == boolean) {
		createBooleanInput(attribute);
	} else switch (attribute.valueHandlingMode) {
		case range:
			break;
		case set:
			break;
	}

	fieldset.appendChild(document.createElement('br'));

}

function createLabel(forInput, text) {
	const label = document.createElement('label');
	label.setAttribute('for', forInput);
	label.textContent = text;
	return label;
}



function createBooleanInput(attribute) {

	fieldset.appendChild(createLabel("", "Yes"));
	fieldset.appendChild(createRadioInput(attribute, true));

	fieldset.appendChild(createLabel("", "No"));
	fieldset.appendChild(createRadioInput(attribute, false));
}

function createInputElement(type, attribute, value) {
	let element = document.createElement("input");
	element.setAttribute('type', type);
	element.setAttribute('name', toInputName(attribute));
	element.setAttribute('value', value);
	return element;
}

function createRadioInput(attribute, value) {
	return createInputElement('radio', attribute, value);
}

function toInputName(attribute) {
	return "attr" + attribute.id;
}

function br() {
	fieldset.appendChild(document.createElement('br'));
}
