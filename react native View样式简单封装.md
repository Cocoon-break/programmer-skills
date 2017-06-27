#简单的封装了一下react native View样式
### 简单的工具


	/**
 	* Created by dujianyang on 2017/6/27.
	 */

	const Styles = {
    padding(padding, top, right, bottom, left){
        return {
            padding: padding,
            paddingTop: top,
            paddingRight: right,
            paddingBottom: bottom,
            paddingLeft: left
        }
    },
    setCenter(value1, value2){
        return {
            alignItems: value1,
            justifyContent: value2
        }
    },
    bgColor(value){
        return {
            backgroundColor: value
        }
    },
    setSize(value1, value2){
        return {
            height: value1,
            width: value2
        }
    },
    border(top, right, bottom, left, all, color){
        return {
            borderTopWidth: top,
            borderRightWidth: right,
            borderBottomWidth: bottom,
            borderLeftWidth: left,
            borderWidth: all,
            borderColor: color
        }
    },
    borderRadius(Radius, TopLeft, TopRight, BottomLeft, BottomRight){
        return {
            borderRadius: Radius,
            borderTopLeftRadius: TopLeft,
            borderTopRightRadius: TopRight,
            borderBottomLeftRadius: BottomLeft,
            borderBottomRightRadius: BottomRight
        }
    },
    margin(top, right, bottom, left){
        return {
            marginTop: top,
            marginRight: right,
            marginBottom: bottom,
            marginLeft: left
        }
    }
	}
	export default Styles
###使用方式
	import Styles from '这里添加Styles文件路径'
	<View style={[
                    Styles.padding(10,10,10,10),
                    Styles.bgColor('blue'),
                    Styles.border(10,5,10,5,0,'red'),
                    Styles.setCenter('center','center')]}>
    </View>
###仅仅是简单的封装了一些通用样式，后续会增加其它的样式。希望大家能提出好的建议。
