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
	
$(document).ready(function () {
	$("#categorySelector").change(function (e) {
		fieldset.innerHTML = "";
		requestData(e);
	});
});

function requestData(e) {
	let categoryId = e.target.value;

	if (categoryId) {
		$.ajax({
			type: "GET",
			url: contextPath + "/AttributeServlet",
			data: {
				"categoryId": categoryId,
				"unassigned": false
			},
			dataType: "json",
			success: populateFieldset
		});
	}
}

function populateFieldset(attributes) {
	for (let i = 0; i < attributes.length; i++) {
		createAttributeInput(attributes[i]);
	}
}

function createAttributeInput(attribute) {
	
	let div = document.createElement("div");
	div.setAttribute("class", "formElement");

	if (attribute.values.length <= 1) {
		return;
	}

	let attributeLabel = createLabel(attribute);
	attributeLabel.setAttribute("class", "formElementLabel");
	div.appendChild(attributeLabel);

	if (attribute.dataTypeIdentifier == boolean) {
		createBooleanInput(div, attribute);
	} else {
		switch (attribute.valueHandlingMode) {
			case range:
				createRangeInput(div, attribute);
				break;
			case set:
				createSetInput(div, attribute);
				break;
		}
	}

	fieldset.appendChild(div);

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

	function createBooleanInput(div, attribute) {

		div.appendChild(createSimpleLabel("Yes"));
		div.appendChild(createRadioInput(attribute, true));

		div.appendChild(createSimpleLabel("No"));
		div.appendChild(createRadioInput(attribute, false));
	}

	function createRangeInput(div, attribute) {
		let min = Number.parseFloat(attribute.values[0].value);
		let max = Number.parseFloat(attribute.values[attribute.values.length - 1].value);
		
		let step = min == Math.trunc(min) && max == Math.trunc(max) ? 1 :
			Math.min(1, (max - min) * 0.01);
		
		let minElement = createInputElementWithAttributes(
			{"type":"range", 
				"name":toParameterName(attribute),
				"min":min,
				"max":max,
				"step":step,
				"value":min,
				"style":"width: 98%"
			});
		
		let maxElement = createInputElementWithAttributes(
			{"type":"range", 
				"name":toParameterName(attribute),
				"min":min,
				"max":max,
				"step":step,
				"value":max,
				"style":"width: 98%"
			});
						
		div.appendChild(minElement);
		div.appendChild(maxElement);
	}

	function createSetInput(div, attribute) {
		let attributeDiv = document.createElement("div");
		attributeDiv.setAttribute("class", "attributeSetSelector");
		
		for (let i = 0; i < attribute.values.length; i++) {
			let valueDTO = attribute.values[i];
			attributeDiv.appendChild(createAttributeInputElement('checkbox', attribute, valueDTO.value));
			attributeDiv.appendChild(createSimpleLabel(valueDTO.value));
		}
		
		div.appendChild(attributeDiv);
	}

	function createInputElement(type, name, value) {
		return createInputElementWithAttributes({"type":type, "name":name, "value":value});
	}
	
	function createInputElementWithAttributes(attributeArray) {
		let element = document.createElement("input");
		
		for (let i in attributeArray) {
			element.setAttribute(i, attributeArray[i]);
		}
		
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
}
