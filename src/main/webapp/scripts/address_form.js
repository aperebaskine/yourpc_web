/**
 * 
 */
const SERVLET_PATH = "/user/UserServlet";

window.onload = function () {
	const COUNTRY_SELECTOR = document.getElementById("country");
	const PROVINCE_SELECTOR = document.getElementById("province");
	const CITY_SELECTOR = document.getElementById("city");
	
	COUNTRY_SELECTOR.addEventListener("change", fetchProvinces());
	document.getElementById("province").addEventListener("change", fetchCities());
	
	if (COUNTRY_SELECTOR.value) {
		COUNTRY_SELECTOR.dispatchEvent(new Event("change"));
		
		if (provinceIdParam) {
			PROVINCE_SELECTOR.addEventListener("DOMNodeInserted", setParam(PROVINCE_SELECTOR, provinceIdParam));
		}
		
		if (cityIdParam) {
			CITY_SELECTOR.addEventListener("DOMNodeInserted", setParam(CITY_SELECTOR, cityIdParam));
		}
	}
	
	function setParam(selector, value) {
		
		return doSetParam;
		
		function doSetParam(event) {
			let options = selector.options;
			for (let i in options) {
				if (value == selector[i].value) {
					selector[i].selected = true;
					event.target.removeEventListener("change", doSetParam);
					event.target.dispatchEvent(new Event("change"));
				}
			}
		}
	}
}

function fetchProvinces() {
	const PROVINCE_SELECTOR = document.getElementById("province");
	
	return function (e) {
		let countryId = e.target.value;
		
		if (countryId) {
			let searchParams = {"action":"fetch-provinces", "country":countryId};
			requestData(PROVINCE_SELECTOR, searchParams);	
		} else {
			PROVINCE_SELECTOR.item(0).selected = true;
			PROVINCE_SELECTOR.setAttribute("disabled");
		}
	}
}

function fetchCities() {
	const CITY_SELECTOR = document.getElementById("city");
	
	return function (e) {
			let provinceId = e.target.value;
			
			if (provinceId) {
				let searchParams = {"action":"fetch-cities", "province":provinceId};
				requestData(CITY_SELECTOR, searchParams);	
			} else {
				CITY_SELECTOR.item(0).selected = true;
				CITY_SELECTOR.setAttribute("disabled");
			}
		}
}

function requestData(updateTarget, searchParams) {

	let request = new XMLHttpRequest();
	let url = new URL(location.origin + contextPath + SERVLET_PATH);

	for (let i in searchParams) {
		url.searchParams.set(i, searchParams[i]);
	}

	request.onreadystatechange = function () {
		if (this.readyState == 4 && this.status == 200) {
			populate(updateTarget, request.responseText);
		}
	};

	request.open("get", url);
	request.send();
}

function populate(target, json) {
	const options = JSON.parse(json)
		.sort((a, b) => a.name < b.name ? -1 : (a.name > b.name ? 1 : 0));
	
	let defaultOption = target.options.item(0);
	
	target.innerHTML = "";
	
	target.appendChild(defaultOption);
	options.forEach((entry) => {
		let option = document.createElement("option");
		option.setAttribute("value", entry.id);
		option.textContent = entry.name;
		target.appendChild(option);
	});
	
	target.removeAttribute("disabled");
	target.dispatchEvent(new Event("DOMNodeInserted"));
}