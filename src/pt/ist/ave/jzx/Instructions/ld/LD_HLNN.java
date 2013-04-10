package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_HLNN extends Instruction {

	public LD_HLNN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		hl16(m_memory.read16(m_pc16));
//		m_pc16 = incinc16(m_pc16);
	
		_cpu.hl16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 10;
		return 10;
	}

}
