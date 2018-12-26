<?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
-->
<!-- $Id$ -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                exclude-result-prefixes="fo"
                xmlns:i18n="java:at.priesch.coinmanager.servicecomponents.i18n.Translator">
    <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
    <xsl:param name="versionParam" select="'1.0'"/>
    <xsl:param name="currentDate"/>
    <xsl:param name="translator" />
    <xsl:decimal-format name="euro" decimal-separator="," grouping-separator="."/>

    <!-- ========================= -->
    <!-- root element: projectteam -->
    <!-- ========================= -->
    <xsl:template match="document">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="0.5cm" margin-bottom="0.5cm"
                                       margin-left="1cm" margin-right="1cm">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="12pt" space-after="5mm" text-align="center">
                        <fo:external-graphic width="250pt" height="150pt" overflow="hidden" display-align="center" content-width="scale-to-fit" scaling="uniform">
                            <xsl:attribute name="src">
                                <xsl:text>url('data:image/jpeg;base64,</xsl:text>
                                <xsl:value-of select="logo"/>
                                <xsl:text>')</xsl:text>
                            </xsl:attribute>
                        </fo:external-graphic>
                    </fo:block>
                    <fo:block font-family="Arial" text-align="center" font-size="18pt" space-after="7mm">
                        <xsl:value-of select="$currentDate"/>
                    </fo:block>
                    <fo:block font-family="Arial" text-align="center" font-size="14pt" space-after="5mm">
                        <xsl:value-of select="i18n:getTranslatedText($translator, 'materialValues')"/>
                    </fo:block>
                    <fo:block font-size="12pt" space-after="5mm">
                            <fo:block font-size="10pt">
                                <fo:table table-layout="fixed" width="100%">
                                    <fo:table-column column-width="proportional-column-width(1)"/>
                                    <fo:table-column column-width="3cm"/>
                                    <fo:table-column column-width="2.5cm"/>
                                    <fo:table-column column-width="3cm"/>
                                    <fo:table-column column-width="proportional-column-width(1)"/>
                                    <fo:table-header background-color="gray">
                                        <fo:table-row>
                                            <fo:table-cell column-number="2" border-style="solid" border-width="0.5pt">
                                                <fo:block text-align="center" font-weight="bold">
                                                    <xsl:value-of select="i18n:getTranslatedText($translator, 'name')"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="solid" border-width="0.5pt">
                                                <fo:block text-align="center" font-weight="bold">
                                                    <xsl:value-of select="i18n:getTranslatedText($translator, 'abbreviation')"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="solid" border-width="0.5pt">
                                                <fo:block text-align="center" font-weight="bold">
                                                    <xsl:value-of select="i18n:getTranslatedText($translator, 'rate')"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </fo:table-header>
                                    <fo:table-body>
                                        <xsl:apply-templates select="internalMaterial"/>
                                    </fo:table-body>
                                </fo:table>
                            </fo:block>
                    </fo:block>
                    <fo:block font-size="12pt" space-after="5mm">
                        <xsl:apply-templates select="summary"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
            <xsl:apply-templates select="page"/>
        </fo:root>
    </xsl:template>
    <!-- ========================= -->
    <!-- child element: member     -->
    <!-- ========================= -->

    <xsl:template match="internalMaterial">
        <fo:table-row>
            <fo:table-cell display-align="center" column-number="2" border-style="solid" border-width="0.5pt">
                <fo:block text-align="center">
                    <xsl:value-of select="materialName"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <fo:block text-align="center">
                    <xsl:value-of select="abbreviation"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <fo:block text-align="right">
                    <fo:table table-layout="fixed" width="100%">
                        <fo:table-column column-width="1cm"/>
                        <fo:table-column column-width="2cm"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="right">EUR</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <xsl:variable name="rate" select="rate"/>
                                        <xsl:value-of select="format-number($rate, '###.##0,00', 'euro')"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

    <xsl:template match="summary">
        <fo:block text-align="center" font-size="12pt" space-before="8mm" space-after="2mm">
            <xsl:value-of select="materialName"/>
        </fo:block>
        <fo:block font-size="10pt">
            <fo:table text-align="center" table-layout="fixed" width="100%" >
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-column column-width="5cm"/>
                <fo:table-column column-width="3cm"/>
                <fo:table-column column-width="proportional-column-width(1)"/>
                <fo:table-body>
                    <fo:table-row>
                        <fo:table-cell border-style="solid" border-width="0.5pt" column-number="2">
                            <fo:block font-family="Arial" text-align="right" font-size="10pt" >
                                <xsl:value-of select="i18n:getTranslatedText($translator, 'amountCoins')"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell border-style="solid" border-width="0.5pt">
                            <fo:block text-align="right" font-size="10pt">
                                <xsl:value-of select="numberCoins"/>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>

                    <fo:table-row>
                        <fo:table-cell border-style="solid" border-width="0.5pt" column-number="2">
                            <fo:block font-family="Arial" text-align="right" font-size="10pt" >
                                <xsl:value-of select="i18n:getTranslatedText($translator, 'denomination')"/>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell border-style="solid" border-width="0.5pt">
                            <fo:block font-size="10pt">
                                <fo:table table-layout="fixed" width="100%">
                                    <fo:table-column column-width="1cm"/>
                                    <fo:table-column column-width="2cm"/>

                                    <fo:table-body>
                                        <fo:table-row>
                                            <fo:table-cell>
                                                <fo:block text-align="right">EUR</fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:block text-align="right">
                                                    <xsl:variable name="denomination" select="denomination"/>
                                                    <xsl:value-of select="format-number($denomination, '###.##0,00', 'euro')"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </fo:table-body>
                                </fo:table>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>

                    <fo:table-row>
                        <fo:table-cell border-style="solid" border-width="0.5pt" column-number="2">
                            <fo:block text-align="right" font-size="10pt">
                                <fo:block font-family="Arial" text-align="right" font-size="10pt" >
                                    <xsl:value-of select="i18n:getTranslatedText($translator, 'valueOfMaterials')"/>
                                </fo:block>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell border-style="solid" border-width="0.5pt">
                            <fo:block font-size="10pt">
                                <fo:table table-layout="fixed" width="100%">
                                    <fo:table-column column-width="1cm"/>
                                    <fo:table-column column-width="2cm"/>

                                    <fo:table-body>
                                        <fo:table-row>
                                            <fo:table-cell>
                                                <fo:block text-align="right">EUR</fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:block text-align="right">
                                                    <xsl:variable name="valueOfMaterials" select="valueOfMaterials"/>
                                                    <xsl:value-of select="format-number($valueOfMaterials, '###.##0,00', 'euro')"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </fo:table-body>
                                </fo:table>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>

                    <fo:table-row>
                        <fo:table-cell border-style="solid" border-width="0.5pt" column-number="2">
                            <fo:block text-align="right" font-size="10pt">
                                <fo:block font-family="Arial" text-align="right" font-size="10pt" >
                                    <xsl:value-of select="i18n:getTranslatedText($translator, 'estimatedValue')"/>
                                </fo:block>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell border-style="solid" border-width="0.5pt">
                            <fo:block font-size="10pt">
                                <fo:table table-layout="fixed" width="100%">
                                    <fo:table-column column-width="1cm"/>
                                    <fo:table-column column-width="2cm"/>

                                    <fo:table-body>
                                        <fo:table-row>
                                            <fo:table-cell>
                                                <fo:block text-align="right">EUR</fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell>
                                                <fo:block text-align="right">
                                                    <xsl:variable name="estimatedValue" select="estimatedValue"/>
                                                    <xsl:value-of select="format-number($estimatedValue, '###.##0,00', 'euro')"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </fo:table-body>
                                </fo:table>
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template match="page">
        <fo:page-sequence master-reference="simpleA4">
            <fo:flow flow-name="xsl-region-body">
                <fo:block text-align="center" font-size="16pt" font-weight="bold" space-after="5mm">
                    <xsl:value-of select="year"/>
                </fo:block>
                <fo:block font-size="10pt">
                    <fo:table table-layout="fixed" width="100%">
                        <fo:table-column column-width="proportional-column-width(1)"/>
                        <fo:table-column column-width="3cm"/>
                        <fo:table-column column-width="1cm"/>
                        <fo:table-column column-width="2.5cm"/>
                        <fo:table-column column-width="2.5cm"/>
                        <fo:table-column column-width="2.5cm"/>
                        <fo:table-column column-width="3cm"/>
                        <fo:table-column column-width="3cm"/>
                        <fo:table-column column-width="proportional-column-width(1)"/>
                        <fo:table-header background-color="gray">
                            <fo:table-row>
                                <fo:table-cell column-number="2" border-style="solid" border-width="0.5pt">
                                    <fo:block text-align="center" font-weight="bold">Name</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="solid" border-width="0.5pt">
                                    <fo:block text-align="center" font-weight="bold">Jahr</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="solid" border-width="0.5pt">
                                    <fo:block text-align="center" font-weight="bold">Nennwert</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="solid" border-width="0.5pt">
                                    <fo:block text-align="center" font-weight="bold">Schätzwert</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="solid" border-width="0.5pt">
                                    <fo:block text-align="center" font-weight="bold">Material</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="solid" border-width="0.5pt">
                                    <fo:block text-align="center" font-weight="bold">Vorne</fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="solid" border-width="0.5pt">
                                    <fo:block text-align="center" font-weight="bold">Hinten</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>
                        <fo:table-body>
                            <xsl:apply-templates select="coin"/>
                        </fo:table-body>
                    </fo:table>
                </fo:block>
            </fo:flow>
        </fo:page-sequence>
    </xsl:template>

    <xsl:template match="coin">
        <fo:table-row>
            <fo:table-cell display-align="center" column-number="2" border-style="solid" border-width="0.5pt">
                <fo:block text-align="center">
                    <xsl:value-of select="name"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <fo:block text-align="center">
                    <xsl:value-of select="year"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <fo:block>
                    <fo:table table-layout="fixed" width="99%">
                        <fo:table-column column-width="0.9cm"/>
                        <fo:table-column column-width="1.4cm"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="right"><xsl:value-of select="currency//name" /></fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <xsl:variable name="denomination" select="denomination"/>
                                        <xsl:value-of select="format-number($denomination, ' ###.##0,00', 'euro')" />
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <fo:block>
                    <fo:table table-layout="fixed" width="99%">
                        <fo:table-column column-width="0.9cm"/>
                        <fo:table-column column-width="1.4cm"/>

                        <fo:table-body>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block text-align="right">EUR</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block text-align="right">
                                        <xsl:variable name="estimatedValue" select="estimatedValue"/>
                                        <xsl:value-of select="format-number($estimatedValue, '###.##0,00', 'euro')" />
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <xsl:apply-templates select="materials//entry"/>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <fo:block text-align="center">
                    <fo:external-graphic width="70pt" height="70pt" overflow="hidden" display-align="center" content-width="scale-to-fit" scaling="uniform">
                        <xsl:attribute name="src">
                            <xsl:text>url('data:image/jpeg;base64,</xsl:text>
                            <xsl:value-of select="front"/>
                            <xsl:text>')</xsl:text>
                        </xsl:attribute>
                    </fo:external-graphic>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell display-align="center" border-style="solid" border-width="0.5pt">
                <fo:block text-align="center">
                    <fo:external-graphic width="70pt" height="70pt" overflow="hidden" display-align="center" content-width="scale-to-fit" scaling="uniform">
                        <xsl:attribute name="src">
                            <xsl:text>url('data:image/jpeg;base64,</xsl:text>
                            <xsl:value-of select="back"/>
                            <xsl:text>')</xsl:text>
                        </xsl:attribute>
                    </fo:external-graphic>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

    <xsl:template match="materials//entry">
        <fo:block text-align="center">
            <fo:table table-layout="fixed" width="99%">
                <fo:table-column column-width="0.9cm"/>
                <fo:table-column column-width="1.4cm"/>

                <fo:table-body>
                    <fo:table-row>
                        <fo:table-cell>
                            <fo:block text-align="right"><xsl:value-of select="material//name"/></fo:block>
                        </fo:table-cell>
                        <fo:table-cell>
                            <fo:block text-align="right">
                                <xsl:variable name="value" select="double"/>
                                <xsl:value-of select="format-number($value, ' ###.##0,00', 'euro')" />
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>
</xsl:stylesheet>

