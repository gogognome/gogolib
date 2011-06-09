/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.xml;

import java.util.AbstractList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * This class offers a {@link NodeList} as a {@link List}, which offers a much
 * easier way to access its contents.
 *
 * @author SanderK
 */
public class GNodeList extends AbstractList<Node> {

    /** The warpped NodeList. */
    private NodeList nodeList;

    /**
     * Constructor.
     * @param nodeList the node list to be wrapped
     */
    public GNodeList(NodeList nodeList) {
        this.nodeList = nodeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node get(int index) {
        return nodeList.item(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return nodeList.getLength();
    }

}
