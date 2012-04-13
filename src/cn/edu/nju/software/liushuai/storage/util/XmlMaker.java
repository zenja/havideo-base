package cn.edu.nju.software.liushuai.storage.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.edu.nju.software.liushuai.storage.exception.SeeGodException;

public class XmlMaker {

	private static DocumentBuilder docBuilder = null;

	public XmlMaker() throws ParserConfigurationException {
		docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}

	private static Document createDocument() {
		if (docBuilder == null) {
			try {
				new XmlMaker();
			} catch (ParserConfigurationException e) {
				// serious error
				// phone
				e.printStackTrace();
			}
		}
		Document doc = docBuilder.newDocument();
		doc.setXmlVersion("1.0");
		return doc;
	}

	public static Document createDocumentWithHeader() {
		return createDocumentWithHeader(null, true);
	}

	public static Document createDocumentWithHeader(String message) {
		return createDocumentWithHeader(message, false);
	}

	public static Document createDocumentWithHeader(String message, boolean succ) {
		Document doc = createDocument();
		Element root = doc.createElement(XmlConst.DOCUMENT_ROOT);
		doc.appendChild(root);
		Element header = createElementChild(doc, root, XmlConst.DOCUMENT_HEADER);
		Element headerStatus = createElementChild(doc, header,
				XmlConst.HEADER_STATUS);
		Element headerMessage = createElementChild(doc, header,
				XmlConst.HEADER_MESSAGE);
		headerStatus.setTextContent(String.valueOf(succ));
		headerMessage.setTextContent(message);
		return doc;
	}

	public static void setDocumentHeader(Document doc, String message)
			throws SeeGodException {
		if (doc != null) {
			NodeList messageList = doc
					.getElementsByTagName(XmlConst.HEADER_MESSAGE);
			if (messageList.getLength() == 1) {
				Node msg = messageList.item(0);
				msg.setTextContent(message);
			} else {
				throw new SeeGodException("Wrong xml format");
			}
		}
	}

	public static void setDocumentHeader(Document doc, boolean succ)
			throws SeeGodException {
		if (doc != null) {
			NodeList messageList = doc
					.getElementsByTagName(XmlConst.HEADER_STATUS);
			if (messageList.getLength() == 1) {
				Node status = messageList.item(0);
				status.setTextContent(String.valueOf(succ));
			} else {
				throw new SeeGodException("Wrong xml format");
			}
		}
	}

	public static void setDocumentHeader(Document doc, String message,
			boolean succ) throws SeeGodException {
		setDocumentHeader(doc, succ);
		setDocumentHeader(doc, message);
	}

	public static Element createElementChild(Document doc, Element parent,
			String tagName) {
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
		return child;
	}
}
