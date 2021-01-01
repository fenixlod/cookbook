/* scripts.js */

//------------------------------------------------------------------------------------------------------------
//------------------------------------------------------VARIABLES---------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
var baseUrl = 'http://localhost:80';
var apiPaths = {
	recipes: '/recipes'
}
var runnigTimeouts = {};
var actionButtons = {};
var availableTags = [];
var availableIngredients = [];
//}-----------------------------------------------------------------------------------------------------------
//--------------------------------------------------------GENERAL---------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function onClickMainTabButton(targetObj) {
	if($(targetObj).is('i')) {
		targetObj = $(targetObj).parent();
	}
	
	var id = $(targetObj).data('content-id');
	$('#main-page-tabs a').removeClass('active');
	$(targetObj).addClass('active');
	switchMainTab(id);
}

function switchMainTab(id) {
	$('#main-page-content > div').hide();
	$('#main-page-content #' + id).show();
	$('#operation-info').text('');
	initializeMainTab(id);
}

function initializeMainTab(tabId) {
	if(tabId == 'recipes-div') {
		initializeRecipePage();
	} else if(tabId == 'create-menu-div') {
		initializeMenuPage();
	}
}
//}-----------------------------------------------------------------------------------------------------------
//-----------------------------------------------GENERAL API CALL FUNCTIONS-----------------------------------
//{-----------------------------------------------------------------------------------------------------------
function callApiUrl(methodType, path, data, onSuccessCallback, onFailCallback) {
	var ajaxRequest = {
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
	
	if(jqXHR.status == 502 || jqXHR.status == 0) {
		setStatusMessage('Услугата не е достъпна в момента. Опитайте отново по-късно', 'red');
	}
}

function buildAPIPath(path) {
	if(Array.isArray(path))
		return baseUrl + path.join('/');
	else
		return baseUrl + path;
}
//}-----------------------------------------------------------------------------------------------------------
//--------------------------------------------------------STATUS----------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function setStatusMessage(message, color, bindElement) {
	var label = '#operation-status';
	
	if(typeof bindElement != 'undefined' && bindElement != null) {
		var parenDiv = $(bindElement);
		while(true) {
			parenDiv = $(parenDiv).parent();
			if(parenDiv.length < 1)
				break;
			
			var statusDiv = parenDiv.find('.operation-status');
			if(statusDiv.length > 0) {
				label = statusDiv[0];
				break;
			}
		}
	}

	$(label).html(message);
	$(label).css('color', color);
	
	if(color == 'blue') {
		$('body').css('cursor', 'progress');
		if(bindElement)
			setStatusIndicator(bindElement,'working');
	} else if(color == 'green') {
		$('body').css('cursor', 'default');
		if(bindElement)
			setStatusIndicator(bindElement,'done');
	} else if(color == 'red') {
		$('body').css('cursor', 'default');
		if(bindElement)
			setStatusIndicator(bindElement,'fail');
	}
}

function setStatusIndicator(object, state) {
	var prepend = $(object).find('i.action-indicator');
	if(prepend.length == 0)
		return;
	
	if(state == 'working') {
		prepend.attr('class','fa fa-spinner fa-spin fa-tab action-indicator');
		if($(object).is('button')) {
			$(object).removeClass('btn-primary btn-success btn-danger').addClass('btn-primary');
			$(object).prop('disabled', true);
			clearTimeout(runnigTimeouts[object]);
		}
	} else if(state == 'done') {
		prepend.attr('class','fa fa-check fa-tab action-indicator');
		if($(object).is('button')) {
			$(object).removeClass('btn-primary btn-success btn-danger').addClass('btn-success');
			$(object).prop('disabled', false);
			runnigTimeouts[object] = setTimeout(setStatusIndicator, 5000, object, 'reset');
		}
	} else if(state == 'fail') {
		prepend.attr('class','fa fa-times fa-tab action-indicator');
		if($(object).is('button')) {
			$(object).removeClass('btn-primary btn-success btn-danger').addClass('btn-danger');
			$(object).prop('disabled', false);
			runnigTimeouts[object] = setTimeout(setStatusIndicator, 5000, object, 'reset');
		}
	} else if('reset') {
		prepend.attr('class','action-indicator');
		if($(object).is('button')) {
			$(object).removeClass('btn-primary btn-success btn-danger');
			$(object).prop('disabled', false);
			clearTimeout(runnigTimeouts[object]);
		}
	}
}

function setErrorStatusMessage(data, alternativeMessage, bindElement) {
	var message = '';
	if(data) {
		message = data.message;
	} else {
		message = alternativeMessage;
	}
	setStatusMessage(message, 'red', bindElement);
}
//}-----------------------------------------------------------------------------------------------------------
//---------------------------------------------------------TAGS-----------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function onAddNewTagEvent(input) {
	var newTag = $(input).val();
	
	if(newTag == '')
		return;

	var tagsDiv = $(input).parents('.tags-main-div').find('.tags-container');
	addTag(newTag, tagsDiv);
	$(input).val('');
}

function addTag(tag, container) {
	$(container).append(
		$('<span>').attr({'class': 'tag-body form-control form-control-sm'}).append([
			$('<label>').attr({'class': 'tag-label'}).append(tag),
			$('<button>').attr({'class': 'btn btn-sm tag-remove-btn fa fa-times', 'type': 'button'}).click(function () { onClickRemoveTagButton(this);})
		])
	);
}

function onClickRemoveTagButton(button) {
	$(button).parent('.tag-body').remove();
}

function getTags(container) {
	var tags = [];
	$(container).find('label').each(function (i, element) {
		tags.push($(element).text());
	});
	return tags;
}
//}-----------------------------------------------------------------------------------------------------------
//--------------------------------------------------INGREDIENTS-----------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function onClickAddIngredientButton(button) {
	var ingredientsDiv = $(button).parents().find('.ingredients-container-div');
	$('#div-templates .ingredient-div').clone().appendTo(ingredientsDiv);
	$(ingredientsDiv).scrollTop($(ingredientsDiv)[0].scrollHeight);
	updateIngredientsAutocomplete($('#recipe-dialog .ingredient-name').last());
}

function onClickRemoveIngredientButton(button) {
	$(button).parents('.ingredient-div').remove();
}

function addIngredient(ingredient, container) {
	var clonedIngredientDiv = $('#div-templates .ingredient-div').clone();
	$(clonedIngredientDiv).find('input.ingredient-name').val(ingredient.name);
	$(clonedIngredientDiv).find('input.ingredient-amount').val(ingredient.amount);
	$(clonedIngredientDiv).find('select.ingredient-unit').val(ingredient.unit);
	$(clonedIngredientDiv).appendTo(container);
}
//}-----------------------------------------------------------------------------------------------------------
//--------------------------------------------------VALIDATION------------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function validateForm(id) {
	var form = $(id);
	var isFormValid = true;
	
	form.find('[required]').each(function (i, element) {
		if(!$(element).val()) {
			setInvalidInput(element);
			isFormValid = false;
		} else {
			clearInvalidInput(element);
		}
	});
	
	return isFormValid;
}

function setInvalidInput(id) {
	$(id).addClass('is-invalid');
}

function clearInvalidInput(id) {
	$(id).removeClass('is-invalid');
}
//}-----------------------------------------------------------------------------------------------------------
//-----------------------------------------------------FILTERS------------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function onClickFilterButton(button) {
	var currentState = $(button).attr('state');
	var nextState = (currentState + 1) % 3;
	setFilterButtonState(button, nextState);
	searchRecipes();
}

function setFilterButtonState(button, state) {
	$(button).attr('state', state);
	$(button).find('i').removeClass();
	
	var classes = '';
	if(state == 0) {
		classes = 'fa fa-square-o state-icon';
	} else if (state == 1) {
		classes = 'fa fa-check btn-green state-icon';
	} else {
		classes = 'fa fa-ban btn-red state-icon';
	}
	$(button).find('i').addClass(classes);
}

function onClickClearFiltersButton(button) {
	var buttons = $(button).parent('.filter-category-div').find('.btn.filter-state');
	$(buttons).each(function(i, btn) {
		if($(btn).attr('state') != 0)
			setFilterButtonState(btn, 0);
	});
	searchRecipes();
}

function onClickClearAllFiltersButton(button) {
	var buttons = $(button).parents('#search-recipe-filters').find('.btn.filter-state');
	$(buttons).each(function(i, btn) {
		if($(btn).attr('state') != 0)
			setFilterButtonState(btn, 0);
	});
	searchRecipes();
}

function collectFiltersState(container) {
	var includes = [], excludes = [];
	$(container).find('.filter-category-body .btn.filter-state').each(function(i, btn) {
		var state = $(btn).attr('state');
		if(state != 0) {
			var tagName = $(btn).find('.filter-name').text();
			if(state == 1)
				includes.push(tagName);
			else if(state == 2)
				excludes.push(tagName);
		}
	});
	return { 'includes': includes, 'excludes': excludes };
}
//}-----------------------------------------------------------------------------------------------------------
//------------------------------------------------------RECIPE------------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function initializeRecipePage() {
	refreshRecipePage();
}

function onClickAddNewRecipeButton() {
	openRecipeDialog('NEW', null);
}

function onClickInsertRecipeButton() {
	if(!validateForm('#recipe-form')) {
		setStatusMessage('Моля попълнете празните полета', 'red', '#insert-recipe-btn');
		return;
	}
	
	var recipe = getRecipeData();
	setStatusMessage('Добавяне на нова рецепта.....', 'blue', '#insert-recipe-btn');
	callApiUrl('POST', apiPaths.recipes, recipe, insertRecipeSuccess, insertRecipeFail);
}

function getRecipeData() {
	return {
		name: $('#recipe-name').val(),
		description: $('#recipe-description').val(),
		preparation: $('#recipe-preparation').val(),
		tags: getTags('#recipe-tags'),
		ingredients: getRecipeIngredients('#recipe-ingredients')
	};
}

function getRecipeIngredients(container) {
	var ingredients = [];
	$(container).find('.ingredient-div').each(function (i, div) {
		var ingredient = {
			name: $(div).find('input.ingredient-name').val(),
			amount: $(div).find('input.ingredient-amount').val(),
			unit: $(div).find('select.ingredient-unit').val()
		};
		ingredients.push(ingredient);
	});
	return ingredients;
}

function insertRecipeSuccess(data) {
	setStatusMessage('Рецептата добавена успешно', 'green', '#insert-recipe-btn');
	cleanRecipeForm();
}

function insertRecipeFail(data) {
	setErrorStatusMessage(data, 'Грешка при опита да се добави нова рецепта', '#insert-recipe-btn');
}

function cleanRecipeForm() {
	$('#recipe-name').val('');
	$('#recipe-description').val('');
	$('#recipe-preparation').val('');
	$('#recipe-tags').empty();
	$('#recipe-ingredients').empty();
	$('#recipe-tags-input').val('');
}

function addSearchRecipeResultTableStyling() {
	var table = 'search-recipe-result-table';
	var filterDiv = $('#div-templates #data-table-search-div').clone().prepend($('#' + table +'_filter input'));
	$('#' + table +'_filter').empty();
	$('#' + table +'_filter').append($(filterDiv));
	$('#' + table +'_filter input').addClass('form-control');
}

function searchRecipes() {
	var filters = {
		tags: collectFiltersState('#filters-tags'),
		ingredients: collectFiltersState('#filters-ingredients')
	};
	setStatusMessage('Търсене.....', 'blue');
	callApiUrl('GET', apiPaths.recipes, filters, searchRecipesSuccess, searchRecipesFail);
}

function searchRecipesSuccess(data) {
	setStatusMessage('Търсенето успешно', 'green');
	var table = $('#search-recipe-result-table').DataTable();
	table.clear();
	
	data.forEach(function(recipe) {
		table.row.add(
			$('<tr>').append([
				$('<td>').append(recipe.id),
				$('<td>').attr({'class': 'minimum-cell'}).append(recipe.name),
				$('<td>').html(buildRecipeDescription(recipe.description)),
				$('<td>').append(recipeTagsToString(recipe.tags)),
				$('<td>').attr({'class': 'maximum-cell'}).append(recipeIngredientsToString(recipe.ingredients)),
				$('<td>').append(buildRecipeActionsBar(recipe))
			])
		);
	});
	
	table.order([]);
	table.draw();
}

function searchRecipesFail(data) {
	setErrorStatusMessage(data, 'Възникна грешка при търсенето на рецепти');
}

function buildRecipeActionsBar(recipe) {
	return $('<div>').attr({'class': 'btn-group btn-group-actions', 'role': 'group', 'style': 'visibility:hidden'}).append([
				$('<button>').attr({'id': recipe.id + '-view','data-recipe-id': recipe.id, 'type' : 'button', 'class': 'btn btn-blue', 'title': 'Виж подробно'}).append([
					$('<i>').attr({'class': 'fa fa-eye'})
				]).click(function () { onClickViewRecipeButton(this);} ),
				$('<button>').attr({'id': recipe.id + '-edit','data-recipe-id': recipe.id, 'type' : 'button', 'class': 'btn btn-green', 'title': 'Редактирай'}).append([
					$('<i>').attr({'class': 'fa fa-pencil'})
				]).click(function () { onClickEditRecipeButton(this);} ),
				$('<button>').attr({'id': recipe.id + '-delete','data-recipe-id': recipe.id, 'type' : 'button', 'class': 'btn btn-red', 'title': 'Изтрий'}).append([
					$('<i>').attr({'class': 'fa fa-trash'})
				]).click(function () { onClickDeleteRecipeButton(this);} )
			]);
}

function buildRecipeDescription(description) {
	if(description.length < 100) {
		return $('<span>').append(description);
	} else {
		var shortDesc = description.substring(0,100) + '...';
		return $('<span>').attr({'title': description, 'class' : 'info-word'}).append(shortDesc);
	}
}

function recipeTagsToString(tags) {
	return tags.join(', ')
}

function recipeIngredientsToString(ingredients) {
	var ingredientNames = [];
	ingredients.forEach(function(ingredient) {
		ingredientNames.push(ingredient.name);
	});
	return ingredientNames.join(', ');
}

function onClickDeleteRecipeButton(button) {
	var id = $(button).data('recipe-id');
	var name = $($(button).parents('tr').find('td')[0]).text();
	actionButtons['recipe-delete'] = '#' + $(button).attr('id');
	$('#confirm-recipe-delete-dialog-name').text(name);
	$('#confirm-recipe-delete-dialog-ok').data('recipe-id', id);
	$('#confirm-recipe-delete-dialog').modal();
}

function onClickConfirmDeleteRecipeButton(id) {
	setStatusMessage('Изтриване на рецепта.....', 'blue', actionButtons['recipe-delete']);
	callApiUrl('DELETE', [ apiPaths.recipes, id ], null, deleteRecipeSuccess, deleteRecipeFail);
}

function deleteRecipeSuccess(data) {
	setStatusMessage('Рецептата изтрита успешно', 'green', actionButtons['recipe-delete']);
	actionButtons['recipe-delete'] = null;
	refreshRecipePage();
}

function deleteRecipeFail(data) {
	setErrorStatusMessage(data, 'Грешка при опита да се изтрие рецепта', actionButtons['recipe-delete']);
	actionButtons['recipe-delete'] = null;
}

function onClickEditRecipeButton(button) {
	var id = $(button).data('recipe-id');
	var name = $($(button).parents('tr').find('td')[0]).text();
	actionButtons['recipe-edit'] = '#' + $(button).attr('id');
	setStatusMessage('Редактиране на рецепта: ' + name, 'blue', actionButtons['recipe-edit']);
	callApiUrl('GET', [ apiPaths.recipes, id ], null, getRecipeSuccess, getRecipeFail);
}

function getRecipeSuccess(data) {
	setStatusMessage('', 'green', actionButtons['recipe-edit']);
	openRecipeDialog('EDIT', data);
	updateIngredientsAutocomplete($('#recipe-dialog .ingredient-name'));
}

function getRecipeFail(data) {
	setErrorStatusMessage(data, 'Грешка при опита да се намери рецептата', actionButtons['recipe-edit']);
	actionButtons['recipe-edit'] = null;
}

function fillRecipeDialog(recipe) {
	$('#recipe-name').val(recipe.name);
	$('#recipe-description').val(recipe.description);
	$('#recipe-preparation').val(recipe.preparation);
	
	var tagDiv = $('#recipe-dialog').find('.tags-container');
	recipe.tags.forEach(function(tag) {
		addTag(tag, tagDiv);
	});
	
	var ingredientDiv = $('#recipe-dialog').find('.ingredients-container-div');
	recipe.ingredients.forEach(function(ingredient) {
		addIngredient(ingredient, ingredientDiv);
	});
}

function onClickSaveRecipeButton() {
	if(!validateForm('#recipe-form')) {
		setStatusMessage('Моля попълнете празните полета', 'red', '#save-recipe-btn');
		return;
	}
	
	var recipe = getRecipeData();
	var recipeId = $(actionButtons['recipe-edit']).data('recipe-id');
	setStatusMessage('Записване на промените.....', 'blue', '#save-recipe-btn');
	callApiUrl('PUT', [ apiPaths.recipes, recipeId ], recipe, saveRecipeSuccess, saveRecipeFail);
}


function saveRecipeSuccess() {
	setStatusMessage('Рецептата записана успешно', 'green', '#save-recipe-btn');
	setStatusMessage('Рецептата записана успешно', 'green', actionButtons['recipe-edit']);
	$('#recipe-dialog').modal('hide');
	actionButtons['recipe-edit'] = null;
	refreshRecipePage();
}

function saveRecipeFail(data) {
	setErrorStatusMessage(data, 'Грешка при опита да се запишат промените', '#save-recipe-btn');
}

function onClickViewRecipeButton(button) {
	var id = $(button).data('recipe-id');
	var name = $($(button).parents('tr').find('td')[0]).text();
	actionButtons['recipe-view'] = '#' + $(button).attr('id');
	setStatusMessage('Преглед на рецепта: ' + name, 'blue', actionButtons['recipe-view']);
	callApiUrl('GET', [ apiPaths.recipes, id ], null, getRecipeViewSuccess, getRecipeViewFail);
}

function getRecipeViewSuccess(data) {
	setStatusMessage('', 'green', actionButtons['recipe-view']);
	openRecipeDialog('VIEW', data);
}

function getRecipeViewFail(data) {
	setErrorStatusMessage(data, 'Грешка при опита да се намери рецептата', actionButtons['recipe-view']);
	actionButtons['recipe-view'] = null;
}

function openRecipeDialog(state, data) {
	cleanRecipeForm();
	$('#recipe-dialog-operation-status').text('');
	var title = '';
	if(state == 'NEW') {
		title = 'Нова рецепта';
		$('#insert-recipe-btn').show();
		$('#save-recipe-btn').hide();
		$('#recipe-dialog fieldset').removeAttr('disabled');
	} else if(state == 'EDIT') {
		title = 'Редактиране на рецепта';
		fillRecipeDialog(data);
		$('#insert-recipe-btn').hide();
		$('#save-recipe-btn').show();
		$('#recipe-dialog fieldset').removeAttr('disabled');
	} else if(state == 'VIEW') {
		title = 'Преглед на рецепта';
		fillRecipeDialog(data);
		$('#insert-recipe-btn').hide();
		$('#save-recipe-btn').hide();
		$('#recipe-dialog fieldset').attr('disabled','disabled');
	}
	$('#recipe-dialog-title').text(title);
	$('#recipe-dialog').modal({'backdrop': 'static'});
}


function loadRecipeFilters() {
	setStatusMessage('Зареждане на филтрите.....', 'blue');
	callApiUrl('GET', [apiPaths.recipes, 'filters'], null, loadRecipeFiltersSuccess, loadRecipeFiltersFail);
}

function loadRecipeFiltersSuccess(data) {
	setStatusMessage('Зареждане на филтрите успешно', 'green');
	availableTags = [];availableIngredients = [];
	for (var filter in data){
		var filterContainer = $('#filters-' + filter  + ' .filter-category-body');
		$(filterContainer).empty();
		for (var key in data[filter]){
			addFilter(filterContainer, key, data[filter][key]);
			if(filter == 'tags')
				availableTags.push(key);
			else if(filter == 'ingredients')
				availableIngredients.push(key);
		}
	}
	updateTagsAutocomplete();
}

function loadRecipeFiltersFail(data) {
	setErrorStatusMessage(data, 'Възникна грешка при зареждането на филтрите');
}

function addFilter(container, filterName, filterCount) {
	$(container).append(
		$('<div>').append([
			$('<button>').attr({'type' : 'button', 'class': 'btn filter-state', 'state': 0}).append([
				$('<i>').attr({'class': 'fa fa-square-o state-icon'}),
				$('<span>').attr({'class' : 'filter-name'}).append(filterName),
				$('<span>').attr({'class' : 'filter-count badge badge-secondary'}).append(filterCount)
			]).click(function () { onClickFilterButton(this);} )
		])
	);
}

function refreshRecipePage() {
	searchRecipes();
	loadRecipeFilters();
}

function updateTagsAutocomplete() {
	$('#recipe-tags-input').autocomplete({
		source: availableTags,
		appendTo: '#recipe-dialog'
	});
}

function updateIngredientsAutocomplete(element) {
	$(element).autocomplete({
		source: availableIngredients,
		appendTo: '#recipe-dialog'
	});
}
//}-----------------------------------------------------------------------------------------------------------
//-------------------------------------------------------MENU-------------------------------------------------
//{-----------------------------------------------------------------------------------------------------------
function initializeMenuPage() {
	populateDefinitionsSelects($('#definitions-container #definitions-body'));
}

function populateDefinitionsSelects(container) {
	var includeTgas = $(container).find('#include-tags');
	var excludeTgas = $(container).find('#exclude-tags');
	var includeIngredients = $(container).find('#include-ingredients');
	var excludeIngredients = $(container).find('#exclude-ingredients');
	
	includeTgas.empty();
	excludeTgas.empty();
	includeIngredients.empty();
	excludeIngredients.empty();
	
	for (var key of availableTags){
		includeTgas.append(new Option(key, key));
		excludeTgas.append(new Option(key, key));
	}
	
	for (var key of availableIngredients){
		includeIngredients.append(new Option(key, key));
		excludeIngredients.append(new Option(key, key));
	}
	
	includeTgas.selectpicker('refresh');
	excludeTgas.selectpicker('refresh');
	includeIngredients.selectpicker('refresh');
	excludeIngredients.selectpicker('refresh');
}
//}
