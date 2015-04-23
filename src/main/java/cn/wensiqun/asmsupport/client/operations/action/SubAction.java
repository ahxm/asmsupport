package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class SubAction extends AbstractBinaryAction {

    public SubAction(KernelProgramBlock block) {
        super(block, Operator.SUB);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.sub(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands[1])));
    }

}
