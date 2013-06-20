package  com.crispico.flower.util.layout {
	
	import org.flowerplatform.flexutil.layout.ViewLayoutData;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	
	/**
	 * @author Cristina
	 * @flowerModelElementId _S5vYoOI-EeGF46ujw3kLCA
	 */
	public class RecycledViews {
		
		/**
		 * @flowerModelElementId _3vMdcOI-EeGF46ujw3kLCA
		 */
		public var normalViews:Dictionary = new Dictionary();
		
		/**
		 * @flowerModelElementId _6veXkOI-EeGF46ujw3kLCA
		 */
		public var editorViews:ArrayCollection = new ArrayCollection();
		
		public function isEmpty():Boolean {	
			for (var key:Object in normalViews) {
				return false;
			}
			if (editorViews.length > 0) {
				return false;
			}
			return true;
		}
		
		public static function getNormalViewKey(view:ViewLayoutData):String {
			var key:String = view.viewId;
			if (view.customData != null) {
				key += "|" + view.customData;
			}
			return key;
		}
	}
	
}