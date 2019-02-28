/*
 * The MIT License
 *
 * Copyright 2017 Google Developer Group Torino.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package it.gdgtorino.hashcode;

import it.gdgtorino.hashcode.io.InputData;
import it.gdgtorino.hashcode.io.OutputData;
import it.gdgtorino.hashcode.model.Photo;
import it.gdgtorino.hashcode.model.Slide;
import it.gdgtorino.hashcode.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Worker class contains the implementation of the real algorithm; all the "magic" happens here.
 * Please note that this is a Singleton class and indeed the inner class WorkerHolder actually holds
 * the instance of Worker, which is basically the only one available in the source code.
 *
 * @author Marco Terrinoni <marco.terrinoni@gmail.com>
 */
public class Worker {

    private InputData inputData; // this will hold the input data
    private OutputData outputData; // this will hold the output data
    private final Utility utils;

    /**
     * Private constructor; it's not available outside this class.
     */
    private Worker () {
        utils = Utility.getInstance();
    }

    /**
     * The method return the same Worker instance all the time.
     *
     * @return Worker
     */
    public static Worker getInstance () {
        return WorkerHolder.INSTANCE;
    }

    /**
     * This inner class contains the unique Worker class instance.
     */
    private static class WorkerHolder {

        private static final Worker INSTANCE = new Worker();
    }

    /**
     * Main method for Worker class; it executes the real algorithm.
     * The structure of this method consists in:
     * 1. initial input acquisition phase;
     * 2. intermediate elaboration;
     * 3. output generation phase.
     */
    public void execute (String inputFile, String outputFile) {
        System.out.println("Main execution starts for " + inputFile + " file");

        // Initial input acquisition
        inputData = utils.read(inputFile);

        //List<Slide> slideshow = new SlideTreeWorker( inputData ).walk();
        //List<String> slideshow = new SlideTreeWorker( inputData ).walk2();
        List<Photo> slideshow = this.iteratif( inputData );

        // Intermediate elaboration
        outputData = new OutputData(slideshow);
        //outputData.setFirstValue(inputData.getFirstValue() + inputData.getSecondValue());
        System.out.println("Output data created: " + outputData.toString());

        // Final output generation
        utils.write(outputData, outputFile);

        System.out.println("Main execution correctly completed");
    }

    public List<Photo> iteratif( InputData intpuData ){
        Utility uti = Utility.getInstance();
        List<Photo> photos = intpuData.getPhotosHorizontales();

        LinkedList<Photo> slideshow = new LinkedList<>();
        slideshow.add( photos.get(0) );
        int totalScore = 0;

        for( int i = 1; i < photos.size(); i++ ){
            System.out.println( i + " / " + photos.size() );
            Photo newPhoto = photos.get( i );
            int bestScore = totalScore;
            int bestPos = 0;

            for( int j = 0; j < slideshow.size(); j++ ){
                int newScore;

                if( j == 0 ){
                    Photo next = slideshow.get( j );
                    newScore = totalScore + uti.calculateScore2( newPhoto.getTags(), next.getTags() );
                }
                else if ( j == slideshow.size() - 1 ){
                    Photo previous = slideshow.get( j-1 );
                    newScore = totalScore + uti.calculateScore2( newPhoto.getTags(), previous.getTags() );
                }
                else {
                    Photo previous = slideshow.get( j-1 );
                    Photo next = slideshow.get( j );
                    newScore = totalScore + uti.calculateScore2( newPhoto.getTags(), previous.getTags() )
                            + uti.calculateScore2( newPhoto.getTags(), next.getTags() )
                            - uti.calculateScore2( next.getTags(), next.getTags() );
                }

                if( newScore > bestScore ){
                    bestScore = newScore;
                    bestPos = j;
                }
            }

            slideshow.add( bestPos, newPhoto );
        }

        return slideshow;
    }
}
