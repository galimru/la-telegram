package org.lizaalert.util;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class HTMLUtils {

    private static final String TAG_A = "a";
    private static final String TAG_BR = "br";

    public static String transform(Element html) {
        final StringBuilder content = new StringBuilder();
        new NodeTraversor(new NodeVisitor() {
            public void head(Node node, int depth) {
                if (node instanceof TextNode) {
                    TextNode textNode = (TextNode) node;
                    content.append(textNode.text());
                } else if (node instanceof Element) {
                    Element element = (Element) node;
                    if (TAG_A.equals(element.tagName())) {
                        content.append("<a href='")
                                .append(element.attr("abs:href"))
                                .append("'>");
                        if (element.text().isEmpty()) {
                            content.append(element.attr("abs:href"));
                        }
                    } else if (content.length() > 0
                            && TAG_BR.equals(element.tagName())) {
                        content.append("\n");
                    }
                }
            }
            public void tail(Node node, int depth) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (TAG_A.equals(element.tagName())) {
                        content.append("</a>");
                    }
                }
            }
        }).traverse(html);
        return content.toString().trim();
    }

}
