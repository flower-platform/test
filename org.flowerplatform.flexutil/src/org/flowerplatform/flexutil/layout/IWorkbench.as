package org.flowerplatform.flexutil.layout {
	import flash.events.IEventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.core.UIComponent;

	public interface IWorkbench {
		function addEditorView(viewLayoutData:ViewLayoutData, setFocusOnView:Boolean = false, existingComponent:UIComponent = null):UIComponent;
		function closeViews(views:ArrayCollection /* of UIComponent */, shouldDispatchEvent:Boolean = true):void;
		function getComponent(viewId:String, customData:String = null):UIComponent;
		function closeView(view:IEventDispatcher, shouldDispatchEvent:Boolean = true):void;
	}
}