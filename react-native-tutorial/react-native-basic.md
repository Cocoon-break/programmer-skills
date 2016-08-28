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