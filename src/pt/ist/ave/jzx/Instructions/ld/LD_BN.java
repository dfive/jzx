package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_BN extends Instruction {

	public LD_BN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_b8 = m_memory.read8(inc16pc());
		_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.inc16pc()));
	}

	@Override
	public int incTstates() {
//		m_tstates += 7;
		return 7;
	}

}
