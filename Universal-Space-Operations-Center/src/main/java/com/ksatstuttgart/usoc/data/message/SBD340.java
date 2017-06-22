/*
 * The MIT License
 *
 * Copyright 2017 KSat Stuttgart e.V..
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
package com.ksatstuttgart.usoc.data.message;

import com.ksatstuttgart.usoc.data.message.dataPackage.Data;
import com.ksatstuttgart.usoc.data.message.dataPackage.Sensor;
import com.ksatstuttgart.usoc.data.message.header.Header;
import com.ksatstuttgart.usoc.data.message.header.MetaData;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valentinstarlinger
 */

@XmlRootElement (namespace = "usoc/")
public class SBD340 {
    private Header header;
    private Data data;
    private ProtocolType protocol;

    public SBD340() {
        header = new Header();
        data = new Data();
    }
    
    public SBD340(SBD340 copy) {
        header = new Header(copy.getHeader());
        data = new Data(copy.getData());
        
        this.protocol = copy.getProtocol();
    }

    @XmlElement ( name = "header" )
    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @XmlElement ( name = "data" )
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @XmlAttribute (name = "protocol")
    public ProtocolType getProtocol() {
        if(protocol == null) return ProtocolType.NONE;
        return protocol;
    }

    public void setProtocol(ProtocolType protocol) {
        this.protocol = protocol;
    }

    /**
     * Returns a String of the data contained in the message that can be used
     * to visualize it.
     * @return A String of the data contained in the message that can be used
     * to visualize it.
     */
    @Override
    public String toString(){
        String s = "SBD340Message (Type = "+this.protocol+")\n";
        
        for (Sensor sensor : data.getSensors()) {
            s+=sensor.getSensorName()+ " (Type = "+sensor.getType()+")\n";
            for (Var var : sensor.getVars()) {
                s+="\t"+var.getDataName()+" ["+var.getUnit()+"]\n";
                for (Long timeStamp : var.getValues().keySet()) {
                    s+="\t\t"+var.getValues().get(timeStamp)+" "+var.getUnit()
                            +"\t| "+timeStamp+" ms\n";
                }
            }
            
        }
        
        return s;
    }
    
    /**
     * Returns the message structure as String excluding the values of the different
     * data points.
     * @return The message structure as String excluding the values of the different
     * data points.
     */
    public String getMessageStructure(){
        String s = "SBD340Message:\nType: "+this.protocol
                +"\nHeader:\n"+this.header.toString()
                +"\nDataPackage\n"+this.data.toString();
        
        return s;
    }
    
    /*
    * AUTOGENERATED hashCode and equals functions!
    */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.header);
        hash = 41 * hash + Objects.hashCode(this.data);
        hash = 41 * hash + Objects.hashCode(this.protocol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SBD340 other = (SBD340) obj;
        if (!Objects.equals(this.getHeader(), other.getHeader())) {
            return false;
        }
        if (!Objects.equals(this.getData(), other.getData())) {
            return false;
        }
        return this.getProtocol() == other.getProtocol();
    }
    
}
