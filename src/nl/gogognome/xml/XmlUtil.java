/*
 * $RCSfile: XmlUtil.java,v $
 * Copyright (c) PharmaPartners BV
 */

package nl.gogognome.xml;

import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * This class implements utility methods for handling XML files.
 *
 * @author SanderK
 */
public class XmlUtil {
    /**
     * Private constructor to avoid instantiation.
     */
    private XmlUtil() {
        // should never be called
    }

    /**
     * Gets a list of {@link Element}s that are children of the specified element
     * that match the specfied sequence of tag names.
     * @param element the element
     * @param tagNames the tag names. A sequence of tag names by slashes ('/').
     * @return the list of matching child elements in the order of the XML file
     */
    public static List<Element> getChildElementByTagNames(Element element, String tagNames) {
        LinkedList<Element> result = new LinkedList<Element>();
        String[] tagNameArray = tagNames.split("/");
        result.add(element);

        // Invariant: result contains the elements that match with the tag names
        //            with index less than i.
        for (int i=0; i<tagNameArray.length; i++) {
            LinkedList<Element> tempList = new LinkedList<Element>();
            String name = tagNameArray[i];
            for (Element elem : result) {
                NodeList nodeList;
                nodeList = elem.getElementsByTagName(name);
                for (int j=0; j<nodeList.getLength(); j++) {
                    tempList.add((Element)nodeList.item(j));
                }
            }
            result = tempList;
        }
        return result;
    }
}
