package json.generator.impl;

import json.generator.AbstractGeneratorChain;
import cn.wensiqun.asmsupport.client.block.ForEach;
import cn.wensiqun.asmsupport.client.block.ProgramBlock;
import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.var.LocVar;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.standard.def.clazz.AClass;

public class ArrayGeneratorChain extends AbstractGeneratorChain {

    @Override
    public boolean match(AClass type) {
        return type.isArray();
    }

    @Override
    protected boolean doGenerate(final GeneratorContext context, ProgramBlock<? extends KernelProgramBlock> block,
            final LocVar encoder, final AClass type, Param value) {
        block.call(encoder, "appendDirect", block.val('['));
        block.for_(new ForEach(value) {
            @Override
            public void body(LocVar e) {
                context.getHeader().generate(context, this, encoder, type.getNextDimType(), e);
                call(encoder, "append", val(','));
            }
        });
        block.call(encoder, "trimLastComma");
        block.call(encoder, "appendDirect", block.val(']'));
        return true;
    }


}
