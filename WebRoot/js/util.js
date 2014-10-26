/*
 * 获取指定元素的样式属性值
 * 参数：
 *	e：表示具体的元素
 *	n：表示要获取元素的脚本样式的属性名，如“width”、“borderColor”等
 * 返回值：返回元素e的样式属性n的值
 */
function getStyleAttrValue(e,n){
	if(e.style[n]){
		//如果在Style对象中存在，说明已显式定义，则返回这个值
		return e.style[n];
	}
	else if(e.currentStyle){
		//否则，如果是IE浏览器，则利用它的私有方法读取当前值
		return e.currentStyle[n];
	}
	//如果是支持DOM标准的浏览器，则利用DOM定义的方法读取
	else if(document.defaultView && document.defaultView.getComputedStyle){
		n = n.replace(/([A-Z])/g,"-$1");	//转换参数的属性名，驼峰转换为连字符
		n = n.toLowerCase();
		var s = document.defaultView.getComputedStyle(e,null);
		if(s)	//如果当前元素的样式属性对象存在
			return s.getPropertyValue(n);	//则获取属性值
	}
	else	//如果都不支持，则返回null
		return null;
}

/*
 * 把getStyleAttrValue()函数获取的样式属性值转化为具体的数字值
 * 参数：
 * 	e：表示具体的元素
 * 	w：表示元素的样式属性值，通过getStyleAttrValue()函数获取
 * 	p：表示当前元素百分比转换为小数的值，以便在上级元素中计算当前元素的尺寸
 * 返回值：返回具体的数字值
 * 注意：只针对宽度和高度，并且单位是“px”、“%”或“auto”，默认是求宽度值，
 * 如果需要求高度值，请把这个函数中出现的“width”更改为“height”。
 */
function realStyleAttrValue(e,w,p){
	var p = arguments[2];
	if(!p) p = 1;
	if(/px/.test(w) && parseInt(w))
		return parseInt(parseInt(w)*p);
	else if(/\%/.test(w) && parseInt(w)){
		var b = parseInt(w) / 100;
		if((p != 1) && p) b *= p;
		e = e.parentNode;
		if(e.tagName == "BODY")
			throw new Error("整个文档结构都没有定义固定尺寸，没法计算了，请使用其他方法获取尺寸。");
		w = getStyleAttrValue(e,"width");
		return arguments.callee(e,w,b);
	}
	else if(/auto/.test(w)){
		var b = 1;
		if((p != 1) && p) b *= p;
		e = e.parentNode;
		if(e.tagName == "BODY")
			throw new Error("整个文档结构都没有定义固定尺寸，没法计算了，请使用其他方法获取尺寸。");
		w = getStyleAttrValue(e,"width");
		return arguments.callee(e,w,b);
	}
	else
		throw new Error("元素或其父元素的尺寸定义了其他的单位");
}