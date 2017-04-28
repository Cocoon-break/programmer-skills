# react native 组件生命周期
## 组件
###1.1 什么是组件？
**【组件】**组件就是有限状态机，通过状态渲染对应的界面了，和iOS一样，React的每个组件也有对应的生命周期，它规定了组件的状态和方法需要在什么阶段进行改变和执行。

当调用React.createClass()创建一个组件类时,你应该提供一个包含有render方法以及可选的其他生命周期方法的 规范(Specifications)对象。
###1.2 什么事有限状态机
**【有限状态机】**表示有限个状态以及在这些状态之间的转移和动作等行为的模型。

**【状态机】**能够记住目前所处的状态，根据当前的状态可以做出相应的决策，并且在进入不同的状态时，可以做不同的操作。就像特别简单的一个例子：一个人的所处的状态，决定了不同的操作，如果一个人进入饿的状态，那么接下来他要进行的操作就是喝水。所以说**【React】**正是利用这个概念，通过管理状态来实现对组件的管理。
## React 的生命周期
简单的说组件分为三种状态，**挂载、更新、卸载**。当首次装载组件时，按顺序依次执行**getDefaultProps、getInitialState、componentWillMount、render 和 componentDidMount**。

![状态图](https://github.com/Cocoon-break/programmer-skills/blob/master/react-native-tutorial/screenShot/001.png)

