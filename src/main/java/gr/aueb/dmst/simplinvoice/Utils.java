package gr.aueb.dmst.simplinvoice;

import gr.aueb.dmst.simplinvoice.enums.AadeDocumentTaxCategory;
import gr.aueb.dmst.simplinvoice.model.CustomUserDetails;
import gr.aueb.dmst.simplinvoice.model.DocumentHeader;
import gr.aueb.dmst.simplinvoice.model.DocumentTax;
import gr.aueb.dmst.simplinvoice.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.WebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;


public class Utils {

    public static User getUserFromWebRequest(WebRequest request) {
        return getUserFromPrincipal(((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal());
    }

    public static User getUserFromHttpServletRequest(HttpServletRequest request) {
        return getUserFromPrincipal(((UsernamePasswordAuthenticationToken)request.getUserPrincipal()).getPrincipal());
    }

    public static User getUserFromPrincipal(Object principal) {

        if(principal instanceof CustomUserDetails)
            return ((CustomUserDetails)principal).getUser();

        return null;
    }

    public static String createMyDataUid(DocumentHeader documentHeader) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(documentHeader.getCompanyProfile().getAfm());
        sb.append(sdf.format(documentHeader.getDate()));
        sb.append(documentHeader.getCompanyProfile().getBranch());
        sb.append(documentHeader.getDocumentSeries().getAadeInvoiceType().aadeCode);
        sb.append(documentHeader.getDocumentSeries().getCode());
        sb.append(documentHeader.getNumber());

        return convertToSha1(sb.toString());
    }

    public static String createMyDataAuthenticationCode(DocumentHeader documentHeader) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(documentHeader.getCompanyProfile().getAfm());
        sb.append(sdf.format(documentHeader.getDate()));
        sb.append(documentHeader.getCompanyProfile().getBranch());
        sb.append(documentHeader.getDocumentSeries().getAadeInvoiceType().aadeCode);
        sb.append(documentHeader.getDocumentSeries().getCode());
        sb.append(documentHeader.getNumber());
        sb.append(documentHeader.getMark());
        sb.append(documentHeader.getTotalFinalValue().toString());
        sb.append(documentHeader.getTotalVatValue().toString());
        sb.append(documentHeader.getCounterPart().getAfm());

        return convertToSha1(sb.toString());
    }

    public static String convertToSha1(String value) {
        return DigestUtils.sha1Hex(value).toUpperCase();
    }

    public static String generateMockMyDataMark() {
        return String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(0, 15);
    }

    public static byte[] toByteArray(BufferedImage bi, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static XMLGregorianCalendar convertDateToXmlGregorianCalendar(Date date) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BigDecimal getAadeDocumentTaxTypeTotalValue(List<DocumentTax> documentTaxes, AadeDocumentTaxCategory.AadeDocumentTaxType type) {
        if(ObjectUtils.isEmpty(documentTaxes))
            return BigDecimal.ZERO;

        return documentTaxes.stream().filter(it -> it.getType().type.equals(type))
                .map(DocumentTax::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
