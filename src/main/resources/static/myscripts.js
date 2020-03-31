function EditContactDetails(elem, address, contact, email) {
	if (elem.value == "Edit") {
		elem.value = "Cancel";
		document.getElementById("contact-details").disabled = false;
		document.getElementById("submit-contact-details").type = "Submit";
	} else if (elem.value == "Cancel") {
		elem.value = "Edit";
		document.getElementById("contact-details").disabled = true;
		document.getElementById("submit-contact-details").type = "Hidden";
		document.getElementById("address").value = address;
		document.getElementById("contact").value = contact;
		document.getElementById("email").value = email;
	}
}

function openNav() {
	document.getElementById("mySidenav").style.display = "block";
}

function closeNav() {
	document.getElementById("mySidenav").style.display = "none";
}

function EditPasswordDetails(elem) {
	if (elem.value == "Edit") {
		elem.value = "Cancel";
		document.getElementById("password-details").disabled = false;
		document.getElementById("submit-password-details").type = "Submit";
	} else if (elem.value == "Cancel") {
		elem.value = "Edit";
		document.getElementById("password-details").disabled = true;
		document.getElementById("submit-password-details").type = "Hidden";
	}
}