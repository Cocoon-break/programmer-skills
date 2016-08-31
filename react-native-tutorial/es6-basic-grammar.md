#es6基础语法
react native 0.18以后全面支持es6语法，建议大家在开发react native 的时候使用es6。这里介绍一下一些在react native常用的es6语法。

##es6简介
 ECMAScript 6.0 简称es6是JavaScript语法的下一代标准，已经在2015年6月正式发布了。ECMAScript 和 JavaScript的关系是，前者是后者的规范，后者是前者的一种实现。其他一些相关这里就不多介绍了。更多的请自行查找资料。
 
##常用语法
###let和const
- const
	
	const用来声明一个只读懂常量。一旦声明了，产量的值就不能改变。

		test(){
			const a="haha"
			a="hehe" //err
		}
												
- let

	let用来声明变量，它的用法类似于**var**，但是所声明的变量，只在let命令所在的代码块内有效。在之前版本规范的js中，js是没有块级的作用域。**在js函数中的var声明，其作用是函数体的全部**，咋一听没什么问题。请看下面一个例子：
	
		function runTowerExperiment(tower, startTime) {
	      var t = startTime;
	      tower.on("tick", function () {
	        ... 使用了变量t的代码 ...
	        if (bowlingBall.altitude() <= 0) {
	          var t = readTachymeter();
	          ...
	        }
	      });
	      ... 更多代码 ...
	    }
上面这个例子中用var声明了两次t变量，最终使用变量t进行运算得道的结构都是NaN.

使用var声明变量还会引起另外一个问题--循环内变量过度共享，看例子
		
	var messages = ["嗨！", "我是一个web页面！", "alert()方法非常有趣！"];
    for (var i = 0; i < messages.length; i++) {
      alert(messages[i]);
    }
这时候会弹出三次的undefined,这里的循环用var声明了i。用let 替代var能解决这个问题。

**总结：**let 是更完美的var

###解构赋值
解构赋值允许使用类似数组或对象字面量的语法将数组和对象的属性赋给各种变量。

- 数组解构
	通常情况下我们是这样访问数组中的元素的
		
		var myArray=[1,2,3,4,5]
		var firstElement=myArray[0]
		var secondElement=myArray[1]
		var endElement= myArray[4]
	使用解构之后我们可以这样取数组
		
		var myArray=[1,2,3,4,5]
		var [firstElement, secondElement,..., endElement]=myArray
	从代码中我们可以直观的感受到解构带来的好处

- 对象解构
	通过解构对象，可以把它的每个属性与不同的变量绑定，首先指定被绑定的属性，然后紧跟一个要解构的对象。
	
		var myObject={name:"haha",sex:'male'}
		var {name:newName}=myObject
		console.log(newName)//haha
如果解构的属性和变量名一致时还可以精简
		
		var myObject={name:"haha",sex:'male'}
		var {name,...other}=myObject
		console.log(name)//haha
		console.log(...other)//{sex:'male'}

如果要解构的属性未定义时可以提供一个默认值，解构null或undefined时会得到类型错误

	var {a}=null //TypeError
	
	var [missing=true]=[]
	console.log(missing)//true
	var {k=1}={}
	console.log(k)//1

###箭头函数
箭头函数是函数的简写

匿名函数写法

	var arr=[1,2,3,4]
	arr.map(function(item,index){
		//	操作
		console.log(item)
		return item
	})
箭头函数写法
	
	var arr=[1,2,3,4]
	arr.map((item,index)=>{
		//	操作
		console.log(item)
		return item
	})
	//直接返回不用写打括号
	arr.map(item=>item)
	//声明一个带参数的
	var arrowFunc = param =>{
		console.log(param)
	}
	arrowFunc('haha')
	
###Promise
- 简介

	promise是异步编程的一种解决方案，比传统的解决方案－－回调函数和事件－－更合理和强大。用new Promise 实例化的promise对象有以下三个状态
	
	"has-resolution" - Fulfilled
	resolve(成功)时。此时会调用 onFulfilled
	
	"has-rejection" - Rejected
	reject(失败)时。此时会调用 onRejected
	
	"unresolved" - Pending
	既不是resolve也不是reject的状态。也就是promise对象刚被创建后的初始化状态等

- 使用
		
		var promise = new Promise(function(resolve, reject) {
    	// 异步处理
    	// 处理结束后、调用resolve 或 reject
    	setTimeout(function () {
            resolve('Async Hello world');
        }, 16);
		});
		promise.then(result=>{
			console.log(result)//Async Hello world
			return 'haha'
		}).then(result=>{
			console.log(result)//haha
		}).catch(err=>{
			console.log(err)
		})
		
promise 可以使用多个then,但是只能使用一个catch

[**更多promise学习**](http://liubin.org/promises-book/#chapter1-what-is-promise)
