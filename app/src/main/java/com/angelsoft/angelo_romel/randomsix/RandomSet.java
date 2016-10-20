package com.angelsoft.angelo_romel.randomsix;

import java.util.Arrays;

public class RandomSet {
    private int minValue;//The minimum value of a random number to be generated.
    private int maxValue;//The maximum value of a random number to be generated.
    private int[] numberSet;//Array that stores the generated random numbers.
    private boolean noRepeat;/*true = does not a number twice in a set. false = a
                                number can be repeated.*/

    /**
     * Default constructor. Assigns default values to class attributes.
     */
    public RandomSet(){
        this.minValue = 1;
        this.maxValue = 50;
        this.numberSet = new int[6];
        this.noRepeat = true;
    }

    /**
     * Overloaded constructor.
     * @param minValue = The minimum value allowed for a random number.
     * @param maxValue = The maximum value allowed for a random number.
     * @param numberSet = The size of the Array that stores the generated random numbers.
     * @param noRepeat = Set to true if generating unique numbers. False otherwise.
     */
    public RandomSet(int minValue, int maxValue, int numberSet, boolean noRepeat){
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.numberSet = new int[numberSet];
        this.noRepeat = noRepeat;
    }

    //Getters
    public int getMinValue(){
        return this.minValue;//Returns the value of the minValue attribute.
    }

    public int getMaxValue(){
        return this.maxValue;//Returns the value of the maxValue attribute.
    }

    public int[] getNumberSet(){
        return this.numberSet;//Returns the array that stores the random numbers.
    }

    public boolean getNoRepeat() {
        return this.noRepeat;//Returns the value of the noRepeat attribute.
    }

    //Setters
    public void setMinValue(int minValue){
        this.minValue = minValue;//Sets the value of the minValue attribute.
    }

    public void setMaxValue(int maxValue){
        this.maxValue = maxValue;//Sets the value of the maxValue attribute.
    }

    public void setNumberSet(int numberSet){
        this.numberSet = new int[numberSet];//Sets the array that stores the random numbers.
    }

    public void setNoRepeat(boolean noRepeat) {
        this.noRepeat = noRepeat;
    }

    //Methods.
    /**
     * Generates the non-repeating random numbers and sort it in ascending order.
     */
    public void draw(){
        if(minValue < maxValue){
            for(int i = 0; i < numberSet.length; i ++){
                if(noRepeat) {//get a non-repeating random number.
                    numberSet[i] = getUniqueRandomNumber();
                }
                else {//get a random number that can appear more than once in a set.
                    numberSet[i] = getRandomNumber();
                }
            }
            sortResult();//
        }
        else{
			/*Throw an exception if the minimum value is equal to or greater than the maximum value of the random
			number to be generated.*/
            throw new IllegalArgumentException("Minimum value must be less than maximum value.");
        }
    }

    /**
     * Generate a unique/non-repeating random number based on the minValue and maxValue attributes.
     * @return = the value of the generated random number.
     */
    private int getUniqueRandomNumber(){
        int randomNumber;
        do{//Keep generating a random number until the number generated is unique.
            randomNumber =  (int) ((Math.random() * ((maxValue - minValue) + 1)) + minValue);
        }while(!isUnique(randomNumber));
        return randomNumber;
    }

    /**
     * Generate a random number based on the minValue and maxValue attributes.
     * @return = the value of the generated random number.
     */
    private int getRandomNumber(){
        return ((int) ((Math.random() * ((maxValue - minValue) + 1)) + minValue));
    }

    /**
     * Search for duplicate using Linear search.
     * @param randomNumber = number to be tested for uniqueness.
     * @return boolean = true if generated random number does not exists in the array. Return false otherwise.
     */
    private boolean isUnique(int randomNumber){
        boolean unique = true;//Assume that the number is unique.
        for(int i = 0; i < numberSet.length; i++){//Loop through the array and compare for duplicate value.
            if(numberSet[i] == randomNumber){//The number is not unique if a match is found, return false.
                unique = false;
            }
        }
        return unique;
    }

    /**
     * Sort the array using the built-in sort() method of the Arrays class. Uses quicksort algorithm for primitive data types.
     */
    @SuppressWarnings("unused")
    private void sortQuick(){
        Arrays.sort(numberSet);//Sort in ascending order.
    }

    /**
     * Sort the array in ascending order using the bubble sort algorithm.
     */
    private void sortResult(){
        int tempHolder;/*Will temporarily store the highest number if the comparison between
						two numbers are in the wrong order.*/
        for(int i=0; i < numberSet.length-1; i++){
            for(int j=1; j < numberSet.length-i; j++){
                if(numberSet[j-1] > numberSet[j]){/*Swap the position of the items if the item on the left
                 									is higher than the item on the right.*/
                    tempHolder=numberSet[j-1];
                    numberSet[j-1] = numberSet[j];
                    numberSet[j] = tempHolder;
                }
            }
        }
    }

}
