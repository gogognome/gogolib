/*
    This file is part of gogolib.

    gogolib is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    gogolib is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with gogolib.  If not, see <http://www.gnu.org/licenses/>.
*/
package nl.gogognome.lib.xml;

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
