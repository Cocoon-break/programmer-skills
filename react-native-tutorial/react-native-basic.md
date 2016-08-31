#react native 预备知识

##react state 和props 说明
- **props**组件的属性，组件可以通过props传进来的值，对组件进行定制化
- **state**组件的状态，组件内自己维护的状态。通过改变state中的值来修改一些绑定的值
	
	例子：				
	
        es6写法
        class Button extends React.Component{
        		constructor(props){
        			super(props)
        			this.state={
        				buttonText:props.buttonText
        			}
        			this.onPress=this.onPress.bind(this)
        		}
        		onPress(){
        			this.setState({buttonText:'改变按钮名称'})
        		}
        		render(){
        			return <TouchableHighlight onPress={this.onPress}>
        				<Text>{this.state.buttonText}</Text>
        			</TouchableHighlight>
        		}
        }
        class Demo extends React.Component{
        		render(){
        		return <View>
        			<Button buttonText={'这个是按钮'}/>
        		</View>
        		}
        }

##react 组件说明
- **getInitialState** 该方法在组件中只会被执行一次，返回一个Object类型，object里的值可以通过this.state.xxx 来获取。

        es5 写法初始化state,获取state中的值可以通过this.state.xxx
        const MyComponent = React.createClass({
	        getInitialState:function(){
	        	return {
	        		a:"haha"
	        	}
	        }
	        componentDidMount(){
	        	console.log(this.state.a)
	        }
        })
        
		es6 写法初始化state,取state中值方式和es5一致
		class MyComponent extends React.Component{
			constructor(props){
				super(props)
				this.state={
					a:"haha"
				}
			}
		}
		
- **getDefaultProps和propTypes** 该方法在组件创建的时候执行一次，用户给props设置默认值,通过this.props.xxx取值

		es5写法		
		const MyComponent = React.createClass({
	        getDefaultProps:function(){
	        	return {
	        		b:"hehe"
	        	}
	        }
	        propTypes:{
	        	b:React.PropTypes.string.isRequired
	        }
	        componentDidMount(){
	        	console.log(this.props.b)
	        }
        })
        
		es6写法
		class MyComponent extends React.Component{
			static defaultProps = {b:"hehe"}
			static propTypes={b:React.PropTypes.string.isRequired}
		}
		
**注：** [更多es5和es6写法差异对比](http://bbs.reactnative.cn/topic/15/react-react-native-%E7%9A%84es5-es6%E5%86%99%E6%B3%95%E5%AF%B9%E7%85%A7%E8%A1%A8)
## react 生命周期
- **componentWillMount**
	
	componentWillMount会在render之前执行，并且只会被执行一次。在这个方法内仍然可以通过setState来更改state。
	
- **render**
	
	react 核心方法整个UI的显示都在这个方法内渲染。该方法只能返回一个元素，并且这个方法是必须写的。当**this.props**或者**this.state**发生改变时，该方法会执行。同时render是一个纯的function，也就是说这个方法内不能setState。
	
- **componentDidMount**

	componentDidMount在render之后执行，可以在这个方法内进行异步的网络请求。同时在这个方法内已经可以通过this.refs.xxx来取得某个组件的实例。（refs在我的理解中相当于java对象的一个实例，可以通过这个实例来执行这个实例内的function）
	
- **componentWillReceiveProps**

	componentWillReceiveProps在props改变的时候被调用，这个方法在最初的生命周期不会调用，只有在props发生改变的时候执行。
	
- **shouldComponentUpdate**

	shouldComponentUpdate在state或者props发生改变时调用，前面说了在state和props改变时render方法会被执行，其实在这中间还有一个function会先执行，那就是shouldComponentUpdate，该方法的返回值为boolean型，当返回true时才会执行render方法，默认返回值为true
	
- **componentWillUpdate**

	componentWillUpdate在更新之前调用，注意在这个方法内不能进行setState
	
- **componentDidUpdate**

	componentDidUpdate在更新之后调用
	
- **componentWillUnmount**

	componentWillUnmount 在组件卸载之前执行，要清除一些东西时这个方法内执行。如 timers

**生命周期总结**

|声明周期|调用次数|能否使用setState|
|:------:|:--------:|:----------:|
|defaultProps/getDefaultProps|1|否|
|constructor/getInitialState|1|否|
|componentWillMount|1|**是**|
|render|>=1|否|
|componentDidMount|1|**是**|
|componentWillReceiveProps|>=0|**是**|
|shouldComponentUpdate|>=0|否|
|componentWillUpdate|>=0|否|
|componentDidUpdate|>=0|否|
|componentWillUnmount|1|否|

**图解**

![shot](http://upload-images.jianshu.io/upload_images/2428275-f08403a3ea1b80f4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)	

**更多react相关知识参考** [**官方文档**](https://facebook.github.io/react/docs/getting-started.html)

在这里推荐大家在开发react native的时候用es6语法，在react native 0.18之后已经全面支持es6了。

推荐一本书 [js的函数式编程](https://www.gitbook.com/book/llh911001/mostly-adequate-guide-chinese/details)  
	