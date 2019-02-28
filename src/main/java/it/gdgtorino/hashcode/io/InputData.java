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
package it.gdgtorino.hashcode.io;

import it.gdgtorino.hashcode.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for input data.
 * Just substitute the local variable with the real ones.
 *
 * @author Marco Terrinoni <marco.terrinoni@gmail.com>
 */
public class InputData {

    // First line values
    private int firstValue;

    // Following lines data
    private List<Photo> photosVerticales;

    private List<Photo> photosHorizontales;

    public InputData () {
        this.photosVerticales = new ArrayList<Photo>();
        this.photosHorizontales = new ArrayList<Photo>();
    }

    public int getFirstValue () {
        return firstValue;
    }

    public void setFirstValue (int firstValue) {
        this.firstValue = firstValue;
    }



    public List<Photo> getPhotosVerticales() {
        return photosVerticales;
    }

    public void setPhotosVerticales(List<Photo> photosVerticales) {
        this.photosVerticales = photosVerticales;
    }

    public List<Photo> getPhotosHorizontales() {
        return photosHorizontales;
    }

    public void setPhotosHorizontales(List<Photo> photosHorizontales) {
        this.photosHorizontales = photosHorizontales;
    }

    public void addPhotosHorizontales(Photo photoHorizontale) {
        this.photosHorizontales.add(photoHorizontale);
    }

    public void addPhotosVerticales(Photo photosVerticale) {
        this.photosVerticales.add(photosVerticale);
    }


    @Override
    public String toString() {
        return "InputData{" +
                "firstValue=" + firstValue +
                ", photosVerticales=" + photosVerticales.toString() +
                ", photosHorizontales=" + photosHorizontales.toString() +
                '}';
    }
}
