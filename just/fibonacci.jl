<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<classfile>
    <magic>classfile</magic>
    <minor_version>0</minor_version>
    <major_version>1</major_version>
    <constant_pool>
        <constant_utf8 id="1">
            <bytes>Code</bytes>
        </constant_utf8>
        <constant_utf8 id="2">
            <bytes>CTest</bytes>
        </constant_utf8>
        <constant_class id="3">
            <name_index>2</name_index>
        </constant_class>
        <constant_utf8 id="4">
            <bytes>main</bytes>
        </constant_utf8>
        <constant_utf8 id="5">
            <bytes>()V</bytes>
        </constant_utf8>
        <constant_name_and_type id="6">
            <!--main: ()V-->
            <name_index>4</name_index>
            <descriptor_index>5</descriptor_index>
        </constant_name_and_type>
        <constant_methodref id="7">
            <!--main: ()V-->
            <class_index>3</class_index>
            <name_and_type_index>6</name_and_type_index>
        </constant_methodref>
        <constant_integer id="8">
            <bytes>1</bytes>
        </constant_integer>
        <constant_utf8 id="9">
            <bytes>fibonacci</bytes>
        </constant_utf8>
        <constant_utf8 id="10">
            <bytes>(I)I</bytes>
        </constant_utf8>
        <constant_name_and_type id="11">
            <!--fibonacci: (I)I-->
            <name_index>9</name_index>
            <descriptor_index>10</descriptor_index>
        </constant_name_and_type>
        <constant_methodref id="12">
            <!--fibonacci: (I)I-->
            <class_index>3</class_index>
            <name_and_type_index>11</name_and_type_index>
        </constant_methodref>
        <constant_integer id="13">
            <bytes>0</bytes>
        </constant_integer>
        <constant_utf8 id="14">
            <bytes>global</bytes>
        </constant_utf8>
        <constant_utf8 id="15">
            <bytes>I</bytes>
        </constant_utf8>
        <constant_fieldref id="17">
            <!--global: I-->
            <class_index>3</class_index>
            <name_and_type_index>16</name_and_type_index>
        </constant_fieldref>
        <constant_name_and_type id="16">
            <!--global: I-->
            <name_index>14</name_index>
            <descriptor_index>15</descriptor_index>
        </constant_name_and_type>
    </constant_pool>
    <this_class>3</this_class>
    <field_info>
        <field>
            <!--global: I-->
            <name_index>14</name_index>
            <descriptor_index>15</descriptor_index>
        </field>
    </field_info>
    <method_info>
        <method>
            <!--main: ()V-->
            <name_index>4</name_index>
            <descriptor_index>5</descriptor_index>
            <attribute_info>
                <attribute>
                    <attribute_name_index>1</attribute_name_index>
                    <info>
                        <max_stack>10</max_stack>
                        <max_locals>1</max_locals>
                        <code>
    LDC_W 8
    INVOKESTATIC 12
    ISTORE 0
    RETURN

</code>
                    </info>
                </attribute>
            </attribute_info>
        </method>
        <method>
            <!--fibonacci: (I)I-->
            <name_index>9</name_index>
            <descriptor_index>10</descriptor_index>
            <attribute_info>
                <attribute>
                    <attribute_name_index>1</attribute_name_index>
                    <info>
                        <max_stack>10</max_stack>
                        <max_locals>6</max_locals>
                        <code>
    LDC_W 13
    ISTORE 1
    LDC_W 8
    ISTORE 2
    LDC_W 13
    ISTORE 3
    LDC_W 8
    ISTORE 4
L1: NOP
    ILOAD 2
    ILOAD 0
    IF_ICMPEQ L2
    ILOAD 3
    ILOAD 4
    IADD
    ISTORE 5
    ILOAD 4
    ISTORE 3
    ILOAD 5
    ISTORE 4
    ILOAD 2
    LDC_W 8
    IADD
    ISTORE 2
    GOTO L1
L2: NOP
    ILOAD 4
    IRETURN
</code>
                    </info>
                </attribute>
            </attribute_info>
        </method>
    </method_info>
</classfile>

