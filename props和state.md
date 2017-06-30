#React Native 之“props”和“state”简单总结
###在react native中我们一般通过两种属性来控制组件，这里包括数据的展示，样式的展示等等。而这两种属性分别是props和state，下面简单的介绍一下这两种属性在react native中事如何使用的。
***
##props
###概念
####大多数组件在创建的时候都需要进行采用各种不同的参数进行自定义，这些被创建的参数我们就称作props 
###使用场景
####我们现在以一个React Native最基础的Image组件为例。当我们在创建一个image的时候，我们可以使用source属性进行控制图片显示
***
##state
###概念
####state顾名思义状态，那么简单的来说，它主要是用来控制一个组件的状态变化，我们可以通过改变state中的数据从而改变组件的状态 
###使用场景
####一般来说，我们需要在constructor 中初始化state（例如：this.state={second:0}）。然后我们可以通过this.setState({second:1})的方式更新state中数据的值，只要在组件中有用到state中second这个值，一旦被改变之后，组件中也会跟着改变。
