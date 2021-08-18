package model_classes;

import entities.Star;

public class StarsManager {
    private final double gravConst = 6.673e-11;

    private Star[] starsArray;

    public StarsManager(Star[] starsArray){
        this.starsArray = starsArray;
    }

    public Star[] getStarsArray(){
        return this.starsArray;
    }

    public void updateAllForces(int secondsPerTimeStep){
        for(int i = 0 ; i < starsArray.length; i++){
            starsArray[i].resetForce();
        }

        for(int i = 0; i < starsArray.length; i++){
            for(int j = 0; j < starsArray.length; j++){
                if(j != i){
                    starsArray[i].addForce(starsArray[j]);
                }
            }
        }

        for(int i = 0; i < starsArray.length; i++){
            starsArray[i].update(secondsPerTimeStep);
        }
    }

    /*===================================================================*/

    public double decideScaling(double HalfCanvasHeight){
        double largest = largestDistance();
        //System.out.println("Ratio: "+ 3 * largest / HalfCanvasHeight);
        return 1.5 * largest / HalfCanvasHeight;
    }

    private double largestDistance(){
        double largest = 0.0;
        for (Star star : starsArray) {
            double distanceX = star.getX();
            double distanceY = star.getY();
            double distance = Math.sqrt( Math.pow(distanceX, 2) + Math.pow(distanceY, 2) );
            if(distance > largest){
                largest = distance;
            }
        }
        return largest;
    }

    /*====================================================================*/

    public double decideTimeScaling(){
        double constant = 0.001;

        double sumTime = 0.0;
        for(Star star: starsArray){
            sumTime += estimateTime(star);
        }
        double estimateTimeScale = sumTime/starsArray.length;

        printTime(constant * estimateTimeScale);
        return constant * estimateTimeScale;
    }

    private double estimateTime(Star star){
        // Using Kepler's Third Law to estimate the "circular" orbit time
        double a = Math.sqrt(Math.pow(star.getX(),2) + Math.pow(star.getY(),2));
        double a_in_AU = a * 1/1.496e11;
        double T_in_years = Math.sqrt(Math.pow(a_in_AU,3));
        return T_in_years * 31558464;       // in seconds
    }

    /*private double sumOfMass(){
        double M = 0.0;
        for(Star star: starsArray){
            M += star.getMass();
        }
        return M;
    }*/

    private void printTime(double time){
        double convertedToYears = time/(3600*24*365.26);
        double convertedToDays = time/(3600*24);
        double convertedToHours = time/3600;

        if(convertedToHours < 1){
            System.out.println(time+" seconds in simulation per frame");
        }else if(convertedToDays < 1){
            System.out.println(convertedToHours+" hours in simulation per frame");
        }else if(convertedToYears < 1){
            System.out.println(convertedToDays+" days in simulation per frame");
        }else{
            System.out.println(convertedToYears+" years in simulation per frame");
        }
    }

    /*===============================================*/
    public void decideRadiiScaling(){
        int smallestMagnitude = Integer.MAX_VALUE;
        for(Star star: starsArray){
            double mass = star.getMass();
            int magnitude = (int) Math.log10(mass);
            if(magnitude < smallestMagnitude){
                smallestMagnitude = magnitude;
            }
        }

        for(Star star: starsArray){
            star.decideRadiusScale(smallestMagnitude);
        }
    }
}
