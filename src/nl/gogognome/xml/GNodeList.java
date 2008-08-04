/*
 * $RCSfile: GNodeList.java,v $
 * Copyright (c) PharmaPartners BV
 */

package nl.gogognome.xml;

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
