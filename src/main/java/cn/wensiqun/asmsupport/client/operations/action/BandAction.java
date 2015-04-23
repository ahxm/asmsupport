package cn.wensiqun.asmsupport.client.operations.action;

import cn.wensiqun.asmsupport.client.def.Param;
import cn.wensiqun.asmsupport.client.def.ParamPostern;
import cn.wensiqun.asmsupport.client.def.param.DummyParam;
import cn.wensiqun.asmsupport.core.block.KernelProgramBlock;
import cn.wensiqun.asmsupport.core.operator.Operator;

public class BandAction extends AbstractBinaryAction {

    public BandAction(KernelProgramBlock block) {
        super(block, Operator.BIT_AND);
    }

    @Override
    public Param doAction(Param... operands) {
        return new DummyParam(block, block.band(ParamPostern.getTarget(operands[0]), 
                ParamPostern.getTarget(operands[1])));
    }

}
