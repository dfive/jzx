package pt.ist.ave.jzx.Instructions;

public class IN_AN extends Instruction {

	public IN_AN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_a8 = m_io.in8((m_a8 << 8) | m_memory.read8(inc16pc()));
		_cpu.setM_a8(_cpu.getM_io().in8((_cpu.getM_a8() << 8) | _cpu.getM_memory().read8(_cpu.inc16pc())));
	}

	@Override
	public int incTstates() {
//		m_tstates += 11;
		return 11;
	}

}
