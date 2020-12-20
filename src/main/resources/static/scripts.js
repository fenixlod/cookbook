/* scripts.js */

//------------------------------------------------------------------------------------------------------------
//------------------------------------------------------VARIABLES---------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
var baseUrl = 'localhost:8080';
var apiPaths = {
	recipes: '/recipes',
	watchdogReport: '/report/watchdog/range'
}
//}-----------------------------------------------------------------------------------------------------------
//--------------------------------------------------------GENERAL---------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function clickSwitchMainTab(targetObj) {
	var $target = targetObj;
	if($target.is('i')) {
		$target = $target.parent();
	}
	var id = $target.data('content-id');

	$('#main-page-tabs a').removeClass('active');
	$target.addClass('active');
	switchToMainTab(id);
}

function switchToMainTab(id) {
	$('#main-page-content > div').hide();
	$('#main-page-content #' + id).show();
	$('#operation-info').text('');
	initializeMainTab(id);
}

function initializeMainTab(tabId) {
	if(tabId == 'add-recipe-div') {
	} else if(tabId == 'search-recipe-div') {
	} else if(tabId == 'recipe-randomizer-div') {
	}
}
//}-----------------------------------------------------------------------------------------------------------
//-----------------------------------------------GENERAL API CALL FUNCTIONS-----------------------------------
//{-----------------------------------------------------------------------------------------------------------
function callApiUrl(methodType, path, data, onSuccessCallback, onFailCallback) {
	var ajaxRequest = {
		path: getBasePath(path),
		url: buildAPIPath(path),
		success: onSuccessCallback,
		error: generalFailCallback,
		failCallback: onFailCallback,
		crossDomain: true,
		xhrFields: { withCredentials: true },
		type: methodType
	};
	
	if(data) {
		if(data instanceof FormData) {
			ajaxRequest.data = data;
			ajaxRequest.enctype = 'multipart/form-data';
			ajaxRequest.contentType = false;
			ajaxRequest.processData = false;
		} else {
			if(ajaxRequest.type == 'GET')
				ajaxRequest.data = data;
			else
				ajaxRequest.data = JSON.stringify(data);
			ajaxRequest.contentType = 'application/json';
		}
	}
	
	$.ajax(ajaxRequest);
}

function generalFailCallback(jqXHR, textStatus, errorThrown) {
	if(this.failCallback) {
		var responseData = null;
		if(jqXHR.responseJSON) {
			responseData = jqXHR.responseJSON;
		} else if(jqXHR.responseText) {
			try {
				responseData = JSON.parse(jqXHR.responseText);
			} catch (err) {
			}
		}
		this.failCallback(responseData, jqXHR.status, textStatus);
	}
	
	if(this.path != apiPaths.login) {
		if(jqXHR.status == 401) {
			reloadToLoginPage();
		} else if(jqXHR.status == 403) {
			setOperationStatus('Access denied. You do not have sufficient privileges!', 'red');
		} else if(jqXHR.status == 502 || jqXHR.status == 0) {
			setOperationStatus('Service is currently unavailable. Please try again later.', 'red');
		}
	} else {
		if(jqXHR.status == 502 || jqXHR.status == 0) {
			setLoginStatus('Service is currently unavailable. Please try again later.', 'red');
		}
	}
}

function buildAPIPath(path) {
	if(Array.isArray(path))
		return BaseUrls.api + path.join('/');
	else
		return BaseUrls.api + path;
}

function getBasePath(path) {
	if(Array.isArray(path))
		return path[0];
	else
		return path;
}
//}-----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------OTHER----------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function setOperationStatus(message, color, identifier) {
	setStatus('#operation-status', message, color, identifier);
}

function setStatus(div, message, color, identifier) {
	$(div).html(message);
	$(div).css('color', color);
	
	if(color == 'blue') {
		$('body').css('cursor', 'progress');
		if(identifier)
			setStatusIndicator(identifier,'working');
	} else if(color == 'green') {
		$('body').css('cursor', 'default');
		if(identifier)
			setStatusIndicator(identifier,'done');
	} else if(color == 'red') {
		$('body').css('cursor', 'default');
		if(identifier)
			setStatusIndicator(identifier,'fail');
	}
}

function addItemInTagsInputContainer(input) {
	var newTag = $(input).val();
	console.log('Tag to add = ' + newTag);
	tagsDiv = $(input).parent().parent().find('.tagsinput-tags-div');
	$(tagsDiv).append(
		$('<span>').attr({'class': 'tagsinput-tag form-control form-control-sm'}).append([
			$('<label>').attr({'class': 'tagsinput-tag-label'}).append(newTag),
			$('<button>').attr({'class': 'btn btn-sm tagsinput-tag-btn fa fa-times'}).click(function () { removeItemFromTagsContainer(this);})
		])
	);
	$(input).val('');
}

function removeItemFromTagsContainer(button) {
	$(button).parent().remove();
}

//}
