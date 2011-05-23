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
