fun main() {
    val input: IntArray = intArrayOf(2,3,5,1,2,3,9,8)
    val rotatenumber: Int = 5
    rotateLeft(input,rotatenumber)
}

fun rotateLeft(arrayInt : IntArray,rotatenumber : Int) {
    var temp  = IntArray(rotatenumber)
    var output = IntArray(arrayInt.size)
    var j: Int= 0

    for (i in 0 until rotatenumber){
        temp[i] = arrayInt[i]
    }
    for (i in rotatenumber..(arrayInt.size-1)){
        output[j] = arrayInt[i]
        j++
    }
    j=0
    for (i in (arrayInt.size-rotatenumber)..arrayInt.size-1){
        output[i] = temp[j]
        j++
    }
    print("Input = ")
    for (element in arrayInt) {
        print (element)
    }
    println("")
    println ("Rotate Number :  $rotatenumber")
    print("Output = ")
    for (element in output) {
        print (element)
    }



    // if (rotatenumber > arrayInt.size ){
    //     forEachIndex { index,value ->
    //         val newIndex = (index + (size - rotatenumber)) % arrayInt.size
    //         output[newIndex] = value
    //     }
    // }

    
    
}

