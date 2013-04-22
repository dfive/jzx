package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class AND_N extends Instruction {

	public AND_N(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		work8 = m_memory.read8(inc16pc());
//		and_a(work8);
		int work8 = _cpu.getM_memory().read8(_cpu.inc16pc());
		_cpu.and_a(work8);
		
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 7;
		return 7;
	}

}
