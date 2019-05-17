<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
   <p><b>Rooms and Residents:</b></p>
   <table border="1">
      <th>Room number</th>
      <th>Room type</th>
      <th>Floor</th>
      <th>Gender</th>
      <th>Notes</th>
      <xsl:for-each select="Room_list/Room">
      <xsl:sort select="@id" />
      <p>Room type: <xsl:value-of select="@roomtype" /></p>
            <tr>
            <td><xsl:value-of select="@id" /></td>
            <td><xsl:value-of select="@roomtype" /></td>
            <td><xsl:value-of select="floor" /></td>
            <td><xsl:value-of select="@gender" /></td>
            <td><xsl:value-of select="notes" /></td>           
            </tr>
      </xsl:for-each>
   </table>
   </html>
</xsl:template>

</xsl:stylesheet>