/*******************************************************************************
 Copyright 2011 Benjamin Fagin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


    Read the included LICENSE.TXT for more information.
 ******************************************************************************/

package unquietcode.tools.logmachine.printers;


import unquietcode.tools.logmachine.Entry;
import unquietcode.tools.logmachine.LogMachinePrinter;

/**
 * @author  Benjamin Fagin
 * @version 12 20, 2010
 */
public class LogMachineHTMLPrinter implements LogMachinePrinter {
	String title;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	

	@Override
	public void printTitle() {
	}

	@Override
	public void printHeader() {
	}

	@Override
	public void printFooter() {
	}

	@Override
	public void printEntry(Entry entry) {
		//return;
	}

/*	public @Override
	String generateHTML(long start, long stop, Node root) { *//*relevant info, like start, stop, root node*//*
		StringBuilder sb = new StringBuilder();

		//title
		sb.append("<html><title>").append(getTitle()).append("</title><body>\n");

		//start time
		sb.append("Started ").append(new Date(start)).append("\n<br/><br/>");

		//table of contents
		sb.append(generateTOCUL(root.getChildren(), 0));
		sb.append("<br/><br/><br/>");

		//main bodies
		sb.append(generateUL(root.getChildren(), "main", 0));

		//closing
		sb.append("<br/>Stopped ").append(new Date(stop)).append("\n");
		sb.append("</body></title></html>");

		return sb.toString();
	}

	protected String generateTOCUL(List<Node> nodes, int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"toc\">");
		for (Node node : nodes) {
			sb.append("\n").append(generateTOCLI(node, ++id));
		}
		sb.append("\n</ul>");
		return sb.toString();
	}

	protected String generateTOCLI(Node node, int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li class=\"toc\">\n");
		sb.append("<a href=\"#").append(id).append("\">").append(node.getGroup()).append("</a>");

		if (node.getChildren().size() > 0)
			sb.append("\n").append(generateTOCUL(node.getChildren(), ++id));

		sb.append("\n</li>");
		return sb.toString();
	}


	protected String generateUL(List<Node> nodes, String className, int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"").append(className).append("\">");
		for (Node node : nodes) {
			sb.append("\n").append(generateLI(node, className, ++id));
		}
		sb.append("\n</ul>");
		return sb.toString();
	}

	protected String generateLI(Node node, String className, int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li class=\"").append(className).append("\">\n");
		sb.append("<a name=\"").append(id).append("\">").append(node.getGroup()).append("</a></br>");

		if (node.getData().size() > 0) {
			sb.append("\n").append(generateEntries(node.getData()));
		}

		if (node.getChildren().size() > 0)
			sb.append("\n").append(generateUL(node.getChildren(), className, ++id));

		sb.append("\n</li>");
		return sb.toString();
	}

	protected String generateEntries(List<Entry> entries) {
		//Collections.sort(entries, m_entryComparator);
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"entry\">");

		for (Entry entry : entries) {
			sb.append("\n").append(generateEntry(entry));
		}

		sb.append("\n</ul>");
		return sb.toString();
	}

	protected String generateEntry(Entry entry) {
		StringBuilder sb = new StringBuilder();
		//TODO parse the timestamp into the default formatter, which the user can override
		if (showTimestamps())
			sb.append("(").append(entry.timestamp).append(")<br/>");

		sb.append(entry.message);

		if (entry.uri != null)
			sb.append("<br/>[").append(entry.uri).append("]");

		sb.append("<br/>");
		return sb.toString();
	}*/
}
