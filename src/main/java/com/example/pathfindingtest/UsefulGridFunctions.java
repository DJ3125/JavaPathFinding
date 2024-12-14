package com.example.pathfindingtest;

public final class UsefulGridFunctions {
    public static double getAnglesFromVelocities(int xVelocity, int yVelocity){
        double angle = Math.atan(-(double)yVelocity/xVelocity);
        if(xVelocity > 0){angle += Math.PI;}
        angle = (angle + Math.TAU)%Math.TAU;
        return angle;
    }
    public static double getAnglesFromTwoPoints(int x1, int y1, int x2, int y2){return UsefulGridFunctions.getAnglesFromVelocities(x2 - x1, y2 - y1);}
    public static double getRoundedCosFromAngle(double angle, int howManyZeros){return UsefulGridFunctions.roundToACertainAmount(Math.cos(angle), howManyZeros);}
    public static double getRoundedSinFromAngle(double angle, int howManyZeros){return UsefulGridFunctions.roundToACertainAmount(Math.sin(angle), howManyZeros);}
    public static double roundToACertainAmount(double numberToRound, int howManyZeros){return Math.round(numberToRound * Math.pow(10, howManyZeros))/Math.pow(10, howManyZeros);}
    public static boolean isSubClass(Class<?> superClass, Class<?> classToExamine){
        Class<?> classObject = classToExamine;
        while(classObject != Object.class && classObject != superClass){classObject = classObject.getSuperclass();}
        return classObject == superClass;
    }
    public static boolean arrayHas(Object[] array, Object search){for(Object i : array){if(i == search){return true;}}return false;}
}
