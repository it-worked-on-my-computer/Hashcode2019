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
package it.gdgtorino.hashcode.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.gdgtorino.hashcode.io.InputData;
import it.gdgtorino.hashcode.io.OutputData;
import it.gdgtorino.hashcode.model.Photo;
import it.gdgtorino.hashcode.model.Slide;

import static it.gdgtorino.hashcode.utils.Constants.MSG_ERR_FIND_INPUT_FILE;
import static it.gdgtorino.hashcode.utils.Constants.MSG_ERR_CREATE_OUTPUT_FILE;
import static it.gdgtorino.hashcode.utils.Constants.MSG_ERR_FIND_OUTPUT_FILE;

/**
 * The Utility class provide some useful methods that can be accessible from other parts of the
 * code.
 * Please note that this is a Singleton class and indeed the inner class UtilityHolder actually
 * holds the instance of Utility, which is basically the only one available in the source code.
 *
 * @author Marco Terrinoni <marco.terrinoni@gmail.com>
 */
public class Utility {

    /**
     * Private constructor; it's not available outside this class.
     */
    private Utility () {
    }

    /**
     * The method return the same Utility instance all the time.
     *
     * @return Worker
     */
    public static Utility getInstance () {
        return UtilityHolder.INSTANCE;
    }

    /**
     * This inner class contains the unique Utility class instance.
     */
    private static class UtilityHolder {

        private static final Utility INSTANCE = new Utility();
    }

    /**
     * Reader method for input acquisition phase; it iterates over the input file and instantiates
     * an InputData class with the input values.
     *
     * @return InputData
     */
    public InputData read (String inputFile) {
        InputData inputData = new InputData(); // input wrapper initialization

        // Input acquisition phase starts here
        ClassLoader classLoader = getClass().getClassLoader(); // classloader useful to localize the file
        File file = new File(classLoader.getResource(inputFile).getFile()); // file loading
        try (
            Reader r = new FileReader(file);
            Scanner s = new Scanner(r)) {
            // First line acquisition
            inputData.setFirstValue(s.nextInt());

            int i = 0;
            while(i < inputData.getFirstValue()){
                String type = s.next();
                Boolean isVertical = type.equals("V");
                int tagCount = s.nextInt();
                int j = 0;
                ArrayList<String> tags = new ArrayList<String>();
                while(j < tagCount){
                    tags.add(s.next());
                    j++;
                }
                Photo p = new Photo(Integer.toString(i), isVertical,!isVertical,tags);
                if(p.isHorizontal()){
                    inputData.addPhotosHorizontales(p);
                }else{
                    inputData.addPhotosVerticales(p);
                }
                i++;
            }



        } catch (IOException ex) {
            System.err.println(MSG_ERR_FIND_INPUT_FILE);
            throw new RuntimeException(MSG_ERR_FIND_INPUT_FILE, ex);
        }

        System.out.println("Input data acquired: " + inputData.toString());

        return inputData;
    }

    /**
     * Writer method for output creation; it takes the information contained in the OutputData
     * instance and generates the output file.
     *
     * @param outputData the OutputData instance that contains the information to be printed in the
     *                   output file.
     */
    public void write (OutputData outputData, String outputFile) {
        // Output acquisition phase starts here
        File file = new File(outputFile); // file loading
        if (!file.exists()) { // if the file doesn't exist create a new one
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.err.println(MSG_ERR_CREATE_OUTPUT_FILE);
                throw new RuntimeException(MSG_ERR_CREATE_OUTPUT_FILE, ex);
            }
        }
        try (
            PrintWriter pw = new PrintWriter(file)) { // use of PrintWriter instead of FileWriter
            pw.println( outputData.slideshow.size() );
            //for( Slide slide : outputData.slideshow ){
            for( Photo photo : outputData.slideshow ){
                pw.println( photo.getId() );
            }
        } catch (IOException ex) {
            System.err.println(MSG_ERR_FIND_OUTPUT_FILE);
            throw new RuntimeException(MSG_ERR_FIND_OUTPUT_FILE, ex);
        }

        System.out.println("Output data file completed");
        System.out.println();
    }

	public int getScore(List<Slide> slides) {
		int resultat = 0;
		for (int i = 0; i < slides.size() - 1; i++) {
			resultat += calculateScore(slides.get(i), slides.get(i + 1));
		}
		return resultat;
	}

	public int calculateScore(Slide slide1, Slide slide2) {
		int tagCommun = 0;
		int tagIndep1 = 0;
		int tagIndep2 = 0;
		int resultat = 0;
		for (String tag : slide1.tags) {
			if (slide2.tags.contains(tag)) {
				tagCommun++;
			}
		}
		tagIndep1 = slide1.tags.size() - tagCommun;
		tagIndep2 = slide2.tags.size() - tagCommun;

		resultat = Math.min(tagIndep1, tagCommun);
		resultat = Math.min(tagIndep2, resultat);

		return resultat;
	}

	public int calculateScore2( List<String> tags1, List<String> tags2 ){
        int tagCommun = 0;
        int tagIndep1 = 0;
        int tagIndep2 = 0;
        int resultat = 0;

        for (String tag : tags1) {
            if (tags2.contains(tag)) {
                tagCommun++;
            }
        }
        tagIndep1 = tags1.size() - tagCommun;
        tagIndep2 = tags2.size() - tagCommun;

        resultat = Math.min(tagIndep1, tagCommun);
        resultat = Math.min(tagIndep2, resultat);

        return resultat;
    }
}
