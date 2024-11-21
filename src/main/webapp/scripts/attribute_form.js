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

	if (attribute.values.length <= 1) {
		return;
	}

	fieldset.appendChild(createLabel(attribute));
	br();

	if (attribute.dataTypeIdentifier == boolean) {
		createBooleanInput(attribute);
	} else {
		switch (attribute.valueHandlingMode) {
			case range:
				createRangeInput(attribute);
				break;
			case set:
				createSetInput(attribute);
				break;
		}
	}

	br();

	function createSimpleLabel(text) {
		let label = document.createElement('label');
		label.textContent = text;
		return label;
	}

	function createLabel(attribute) {
		const label = document.createElement('label');
		label.textContent = attribute.name;
		return label;
	}

	function createBooleanInput(attribute) {

		fieldset.appendChild(createSimpleLabel("Yes"));
		fieldset.appendChild(createRadioInput(attribute, true));

		fieldset.appendChild(createSimpleLabel("No"));
		fieldset.appendChild(createRadioInput(attribute, false));
	}

	function createRangeInput(attribute) {
		let element = document.createElement("input");
		element.setAttribute('type', 'range');
		element.setAttribute('name', toParameterName(attribute));
		element.setAttribute('min', Number.parseFloat(attribute.values[0].value));
		element.setAttribute('max', Number.parseFloat(attribute.values[attribute.values.length - 1].value));
		fieldset.appendChild(element);
	}

	function createSetInput(attribute) {
		for (let i = 0; i < attribute.values.length; i++) {
			let valueDTO = attribute.values[i];
			fieldset.appendChild(createAttributeInputElement('checkbox', attribute, valueDTO.id));
			fieldset.appendChild(createSimpleLabel(valueDTO.value));
			br();
		}
	}

	function createInputElement(type, name, value) {
		let element = document.createElement("input");
		element.setAttribute('type', type);
		element.setAttribute('name', name);
		element.setAttribute('value', value);

		return element;
	}

	function createAttributeInputElement(type, attribute, value) {
		return createInputElement(type, toParameterName(attribute), value);
	}

	function createRadioInput(attribute, value) {
		return createAttributeInputElement('radio', attribute, value);
	}

	function toParameterName(attribute) {
		return ["attr", attribute.dataTypeIdentifier, attribute.id].join(".");
	}

	function br() {
		fieldset.appendChild(document.createElement('br'));
	}
}
