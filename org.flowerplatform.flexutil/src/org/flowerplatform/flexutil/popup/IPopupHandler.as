package org.flowerplatform.flexutil.popup {
	import mx.core.IVisualElement;

	public interface IPopupHandler {
		function setTitle(value:String):IPopupHandler;
		function setWidth(value:int):IPopupHandler;
		function setHeight(value:int):IPopupHandler;
		function setPopupContent(value:IPopupContent):IPopupHandler;
		
		function show(modal:Boolean = true):void;
		function showModalOverAllApplication():void;
	}
}